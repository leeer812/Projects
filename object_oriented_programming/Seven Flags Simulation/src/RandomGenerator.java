/**
 * The <code>RandomGenerator</code> class generates an instance of the
 * RandomGenerator object and supplies two methods to randomly select a ride
 * from an array of rides <code>Ride[]</code>. Given an array of rides, a
 * ride will be selected within the array with equal chance for each ride.
 * Given both an array of rides and an array of probabilities
 * <code>double[]</code>, a ride will be selected within the array with the
 * respective probabilities in the array of probabilities.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class RandomGenerator
{
    /**
     * Selects a ride randomly from <code>rides</code> where each ride has an
     * equal chance of being selected
     * 
     * @param rides
     * The <code>ride[]</code> that the <code>ride</code> will be selected from
     * 
     * @return
     * Returns the <code>ride</code> in <code>rides</code> at the index given
     * by <code>chance</code>
     */
    public static Ride selectRide(Ride[] rides)
    {
        int chance = (int) Math.random() * rides.length;
        chance = (int)(Math.random() * rides.length);
        return rides[chance];
    }
    
    /**
     * Selects a ride randomly from an input array of rides with the
     * probability of each ride being chosen given by an array of doubles.
     * 
     * @param rides
     * The <code>ride[]</code> that the <code>ride</code> will be selected from
     * 
     * @param probabilities
     * The <code>double[]</code> that the probabilities will be determined from
     * 
     * <dt>Postcondition:
     *    <dd>A <code>ride</code> is returned from <code>rides</code> based on
     *    the number generated from <code>chance</code>. Depending on the
     *    number generated, a ride will be selected from an index corresponding
     *    to the probability associated with the corresponding value in
     *    <code>probabilities</code>.
     * 
     * @return
     * Returns a ride, selected at random, from <code>rides</code>
     */
    public static Ride selectRide(Ride[] rides, double[] probabilities)
    {
        double chance = Math.random();
        double boundary1 = probabilities[0];
        double boundary2 = probabilities[1];
        double boundary3 = probabilities[2];
        // Depending on which interval chance is in, the respective ride is
        // returned
        if (chance >= 0 && chance <= boundary1)
            return rides[0];
        else if (chance > boundary1 && chance <= boundary1+boundary2)
            return rides[1];
        else if (chance > boundary1+boundary2 && chance <= boundary1+boundary2+boundary3)
            return rides[2];
        else 
            return rides[3];
    }
}
