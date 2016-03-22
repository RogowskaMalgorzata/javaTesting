package lab03;


public interface IMyList {
	public void add(Candy c);
	Candy get(int index);
	public IMyList getAll(); 
	public int size();
}
