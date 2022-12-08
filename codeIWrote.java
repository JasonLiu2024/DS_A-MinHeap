/**
	 * Swap the Entries at indices i and j in the heap storage.
	 * 
	 * @param i position of one object to swap
	 * @param j position of other object to swap
	 */
	private void swap(int i, int j) {
		ticker.tick();
		HeapPQEntry tempPQEntry = entries.get(i);
		entries.set(i, entries.get(j));
		entries.set(j, tempPQEntry); 
		// Remember to update the Entries to reflect their new positions.
		int temp = entries.get(i).position;
		entries.get(i).position = entries.get(j).position;
		entries.get(j).position = temp;
		
	}
@Override
	public PQEntry<E, P> insert(E thing, P priority) {
		ticker.tick();
		entries.ensureCapacity(40);
		HeapPQEntry insert = new HeapPQEntry(thing, priority);
		int index = size();
		//entries.ensureCapacity(100);
		entries.add(insert);
		insert.position = index;
		repairHeapAtEntry(insert);
		return insert; 
	}
	@Override
	public PQEntry<E, P> extractMin() {
		ticker.tick();
		if (this.isEmpty()) {
			return null;
		}  
		HeapPQEntry top = entries.get(0); //get is a method for the entries arraylist, so we use ENTRIES.
		HeapPQEntry bottom = entries.get(entries.size() - 1);
		entries.remove(entries.size() - 1);
		if(!this.isEmpty()) { //isEmpty is a method for the MinHeap, so we use THIS.
			entries.set(0, bottom); //this is copying from the last item to the root (overwrites the root)
			//need to remove that last item
			entries.get(0).position = 0;
			bubbleDown(0);
		}
        return top;
	}
	/**
	 * Remove Entry stored at the specified index from the heap,
	 *  and repair the heap as necessary.
	 *  
	 * @param index index in array storage at which to remove Entry
	 */
	private void removeAtIndex(int index) {
		//HeapPQEntry remove = entries.get(index);
		ticker.tick();
		entries.remove(index);
		//return remove;
		// NOTE: implementing this method is optional.
		//       Full credit may be earned for the assignment
		//       without implementing this method.
	}
	/**
	 * Index of the Entry in heap storage that is the
	 *  parent of the index passed as a parameter.
	 * @param index current index
	 * @return parent index
	 */
	private int parentIndex(int index) {
		ticker.tick();
		if(index <= 0) {
			return INVALID_POSITION;
		}
		int result = (index - 1)/2;
		return result;
	}
	/**
	 * Index of the Entry in heap storage that is the
	 *  left child of the index passed as a parameter.
	 * @param index current index
	 * @return left child index
	 */
	private int leftChildIndex(int index) {
		ticker.tick();
		int result = 2 * index + 1;
		if(result >= size()){
			return INVALID_POSITION;
		} else {
			return result;
		}
	}
	/**
	 * Index of the Entry in heap storage that is the
	 *  right child of the index passed as a parameter.
	 * @param index current index
	 * @return right child index
	 */
	private int rightChildIndex(int index) {
		ticker.tick();
		int result = 2 * index + 2;
		if(result >= size()){
			return INVALID_POSITION;
		} else {
			return result;
		}
	}
/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter downward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */
	private void bubbleDown(int startIndex) {
		ticker.tick();
		int indexL = leftChildIndex(startIndex);
		int indexR = rightChildIndex(startIndex);
		if(indexL == INVALID_POSITION) {
			return;
		}
		if(indexR == INVALID_POSITION) {
			if(entries.get(startIndex).priority.compareTo(entries.get(indexL).priority) > 0) {
				swap(indexL, startIndex);
				bubbleDown(indexL);
			}
		} else {
			if(entries.get(indexL).priority.compareTo(entries.get(indexR).priority) < 0) {
				if(entries.get(startIndex).priority.compareTo(entries.get(indexL).priority) > 0) {
					swap(indexL, startIndex);
					bubbleDown(indexL);
				}
			} else {
				if(entries.get(startIndex).priority.compareTo(entries.get(indexR).priority) > 0) {
					swap(indexR, startIndex);
					bubbleDown(indexR);
				}
			}	
		}
	}
	/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter upward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */
	private void bubbleUp(int startIndex) {
		ticker.tick();
		int indexP = parentIndex(startIndex);
		if(indexP == INVALID_POSITION) {
			return;
		}
		if(entries.get(startIndex).priority.compareTo(entries.get(indexP).priority) < 0) {
			swap(startIndex, indexP);
			bubbleUp(indexP);
		}
	}
