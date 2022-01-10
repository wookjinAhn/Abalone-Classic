package abaloneClassic;

public class Pair
{
	private Integer row;
	private Integer column;
	
	Pair()
	{
		this.row = 0;
		this.column = 0;
	}
	
	Pair(int row, int column) 
	{
		this.row = Integer.valueOf(row);
		this.column = Integer.valueOf(column);
    }

    public Integer getRow()
    {
        return row;
    }

    public Integer getColumn()
    {
        return column;
    }
    
    public int getRowInt()
    {
        return row.intValue();
    }

    public int getColumnInt()
    {
        return column.intValue();
    }
    
    public void setRow(int row)
    {
    	this.row = row;
    }
    
    public void setColumn(int column)
    {
    	this.column = column;
    }
    
    public void setPair(int row, int column)
    {
    	this.row = Integer.valueOf(row);
    	this.column = Integer.valueOf(column);
    }
}
