package abaloneClassic;

import java.util.Comparator;

public class IndexComparator implements Comparator<Pair>
{
	public int x;
	
	@Override
	public int compare(Pair p1, Pair p2) 
	{
		return (p1.getRow() != p2.getRow() ? p1.getRow() - p2.getRow() : p1.getColumn().compareTo(p2.getColumn()));
	}
}

