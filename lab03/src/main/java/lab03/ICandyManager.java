package lab03;

import java.util.List;

public interface ICandyManager {
	public void add(Candy c);
	public Candy get(int index);
	public List<Candy> getAll();
}
