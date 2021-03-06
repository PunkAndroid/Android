package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMap {
	public static List<Map<String,String>> getMaps(){
		List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "xigua");
		map1.put("price", "$4.3");
		map1.put("address", "hebei");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "xiangjiao");
		map2.put("price", "$4.0");
		map2.put("address", "tianjin");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("name", "caomei");
		map3.put("price", "$4.9");
		map3.put("address", "fujian");
		listMaps.add(map1);
		listMaps.add(map2);
		listMaps.add(map3);
		return listMaps;
	}
}
