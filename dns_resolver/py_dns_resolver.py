import re
import dns# as dns
import dns.query
import dns.message
import dns.name
import time
import sys

# open output file
f = open('mydig_output.txt', 'w')

# check for command-line arguments and setting default values for domain and timeout (in seconds)
if len(sys.argv) > 1:
    domainName = str(sys.argv[1])
else:
    domainName = "google.com"
if len(sys.argv) > 2:
    timeout = sys.argv[2]
else:
    timeout = 5

domain = dns.name.from_text(domainName)
root = "199.9.14.201"
destination = root

if not domain.is_absolute():
    domain = domain.concatenate(dns.name.root)

timedOut = False
foundANS = False
foundAnswer = False
ansSection = False
begin = False
dataType = dns.rdatatype.A
totalTime = 0

while not foundANS:
    start = time.time()
    query = dns.message.make_query(domain, dataType)
    response = dns.query.udp(query, destination, timeout)
    totalTime += time.time() - start
    if totalTime > timeout:
        timedOut = True
        break
    responseSize = sys.getsizeof(response)
    responseMessage = response.to_text().split('\n')

    for i in range(0, len(responseMessage)):
        if responseMessage[i] == ';ANSWER' and i < len(responseMessage) - 1 and responseMessage[i + 1] != ';AUTHORITY':  # we have resolved the DNS
            foundANS = True
            for j in range(i + 1, len(responseMessage)):
                if responseMessage[j][0] != ';':  # this is still an answer entry
                    if not foundAnswer:
                        foundAnswer = True
                        f.write('QUESTION SECTION:\n')
                        for x in range(0, len(responseMessage)):  # print the question section
                            if responseMessage[x] == ';QUESTION':
                                f.write('%s\n' % responseMessage[x+1])
                                break
                        f.write('\nANSWER SECTION:\n')
                    f.write('%s\n' % responseMessage[j])
                    resolved = responseMessage[j]
                else:
                    break  # no more answer entries
            break
        elif responseMessage[i] == ';ADDITIONAL':
            if i < len(responseMessage) - 1:  # we have found the next name server
                for y in range(i, len(responseMessage)):
                    resolved = responseMessage[y + 1]
                    resolvedMessage = re.search('(?<=A[^A])\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}', resolved)
                    if resolvedMessage:
                        break
                    else:
                        resolvedMessage = re.search('(?<=CNAME ).+', resolved)
                        if resolvedMessage:
                            break
                break
            else:
                for j in range(0, len(responseMessage)):
                    if responseMessage[j] == ';AUTHORITY':
                        for p in range(j, len(responseMessage)):
                            resolved = responseMessage[j + 1]
                            resolvedMessage = re.search('(?<=NS ).+', resolved)
                            if resolvedMessage:
                                if not foundAnswer:
                                    foundAnswer = True
                                    f.write('QUESTION SECTION:\n')
                                    for x in range(0, len(responseMessage)):  # print the question section
                                        if responseMessage[x] == ';QUESTION':
                                            f.write('%s\n' % responseMessage[x + 1])
                                            break
                                    f.write('\nANSWER SECTION:\n')
                                f.write('%s\n' % responseMessage[j+1])
                                break
                            else:
                                resolvedMessage = re.search('(?<=CNAME ).+', resolved)
                                if resolvedMessage:
                                    break
                                else:
                                    resolvedMessage = re.search('(?<=A[^A])\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}', resolved)
                        break

    resolvedMessage = re.search('(?<=CNAME ).+', resolved)
    if resolvedMessage:
        foundANS = False
        domain = dns.name.from_text(resolvedMessage[0])
        if not domain.is_absolute():
            domain = domain.concatenate(dns.name.root)
        destination = root
        dataType = dns.rdatatype.A
    else:
        resolvedMessage = re.search('(?<=A[^A])\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}', resolved)
        if resolvedMessage:
            destination = resolvedMessage[0]  # resolve next IP Address
            dataType = dns.rdatatype.A
        else:
            resolvedMessage = re.search('(?<=NS ).+', resolved)
            if resolvedMessage:
                foundANS = False
                domain = dns.name.from_text(resolvedMessage[0])
                destination = root
                if not domain.is_absolute():
                    domain = domain.concatenate(dns.name.root)
                dataType = dns.rdatatype.NS

if not timedOut:
    f.write(f'\nQUERY TIME: {round(totalTime*1000)} msec\n')
    f.write(f'WHEN: {time.strftime("%c")}\n')
    f.write(f'MSG SIZE rcvd: {responseSize}\n')
else:
    f.write(f'The dns request for {domain} timed out after {timeout} seconds when querying {destination}')
