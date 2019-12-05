package strategies;

import java.util.*;

import automail.MailItem;
import automail.PriorityMailItem;
/**store undelivered mailitems*/
public class MailPool implements IMailPool {

	private class PoolItem implements Comparable<PoolItem> {
		private int priority;
		private int destination;
		private MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public PoolItem(MailItem mailItem) {
			priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}

		@Override
		public int compareTo(PoolItem o) {
			int order = 0;
			if (priority < o.priority) {
				order = 1;
			} else if (priority > o.priority) {
				order = -1;
			} else if (destination < o.destination) {
				order = 1;
			} else if (destination > o.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	private List<PoolItem> pool;

	public MailPool(){
		// Start empty
		pool = new LinkedList<>();
	}

	@Override
	/**add new mailItem into mailPool
	 * @param mailItem the new item
	 */
	public void addToPool(MailItem mailItem) {
		PoolItem item = new PoolItem(mailItem);
		pool.add(item);
		Collections.sort(pool);
	}

	@Override

	public boolean isEmpty() {
		return pool.isEmpty();
	}

	@Override
	public MailItem peekPool() {
		return pool.get(0) != null ? pool.get(0).mailItem : null;
	}

	@Override
	public MailItem popPool() {
		PoolItem item = pool.remove(0);
		return item != null? item.mailItem: null;
	}
}
