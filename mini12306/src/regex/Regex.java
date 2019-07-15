package regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	public List<String> getGroups(String content,String pattern)
	{
		List<String> list=new ArrayList<>();
		
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(content);
		
		while(m.find())
		{
			list.add(m.group());
		}
		
		return list;
	}
}
