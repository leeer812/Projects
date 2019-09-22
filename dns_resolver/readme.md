# DNS Resolver

DNS, or domain name system, is the standardized system for how internet services translate human-readable domain names such as google.com into IP addresses such as 8.8.8.8. URLs like *google.com* are entered in web browsers, but web browsers do not navigate to domain names, web browsers connect to server IP addresses. The process in which the IP address of a website is obtained with the original domain name is called resolving DNS. 

First, the web browser sends a query to the local DNS resolver to attempt to resolve the domain name for your URL. This query might be cached within the local DNS resolver, but it typically ends up being passed to a root DNS server to be resolved further. The root DNS server determines where to send the query next in order to resolve the query for an authoritative IP address. The query is recursively resolved until the IP address is found and sent back to the web browser so that it can display the relevant web page.

This program can be used to obtain the authoritative IP address of a domain name through the following usage:

python py_dns_resolver.py 'domainname.com'