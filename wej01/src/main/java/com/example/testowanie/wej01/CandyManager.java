package com.example.testowanie.wej01;

import java.util.ArrayList;
import java.util.List;

public class CandyManager {
	List<Candy> list = new ArrayList<Candy>();
	
	public void add(Candy c) {
		list.add(c);
	}
	
	public List<Candy> getAll() {
		return list;
	}
}
