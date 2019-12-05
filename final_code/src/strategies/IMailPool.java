package strategies;

import automail.MailItem;
import automail.Robot;
import exceptions.ItemTooHeavyException;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public interface IMailPool {
	
	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
    void addToPool(MailItem mailItem);

	/**
	 * test if the pool is empty
	 * @return whether the pool is empty
	 */
    boolean isEmpty();

	/**
	 * Peek the first item in the pool
	 * @return the first item in the pool
	 */
	MailItem peekPool();

	/**
	 * remove and return the first item in the pool
	 * @return the first item in the pool
	 */
	MailItem popPool();


}
