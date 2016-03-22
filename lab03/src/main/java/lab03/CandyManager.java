package lab03;

public class CandyManager {
	private IMyList list;
	
	public CandyManager(IMyList list) {
		super();
		this.list = list;
	}

	public void add(Candy c) {
		list.add(c);
	}
	
	public Candy get(int index) {
		return list.get(index);
	}
	
	public IMyList getAll() {
		return list.getAll();
	}
}
