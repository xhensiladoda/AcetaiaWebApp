package model;
import java.util.ArrayList;
import java.util.List;

public class DBList {
	
	private List<String> data;
	
	public DBList(){
		data =new ArrayList<String>();
	}
	
	public void setData(String s){
		this.data.add(s);
	}
	
	public String getData(int i){
		return this.data.get(i);
	}
	
	public int getSize(){
		return this.data.size();
	}
	
	public List<String> getAllData(){
		return this.data;
	}
}
