package testRegex;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import regex.Regex;

public class TestRegex {
	Regex r=new Regex();
	
	@Test
	public void testP()
	{
		String content="2019-07-16:{2019-07-17}";
		String pattern="2019-[01][0-9]-[0-9][0-9]";
		List<String> result=r.getGroups(content, pattern);
		List<String> answer=new ArrayList<>();
		answer.add("2019-07-16");
		answer.add("2019-07-17");
		//Assert.assertEquals(result, answer);
		for(int i=0;i<result.size();i++)
		{
			System.out.println(result.get(i));
		}
	}
}
