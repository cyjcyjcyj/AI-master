package com.haha.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haha.dao.RuleDao;
import com.haha.model.Rule;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class InferenceService {
	
	private ArrayList<String> inputList;

	private ArrayList<Rule> rulesList;//������ж������������淶����

	private ArrayList<String> resultbase;//����⣬��ŵ�ǰ������Ϊ���۵Ķ��Ｏ

	public InferenceService(ArrayList<String> inputList) {
		this.inputList = inputList;
		rulesList = (ArrayList<Rule>) new RuleDao().getAllRules();
		resultbase = new RuleDao().getResultBase();
	}

	public String MatchAndFindResult() {
		boolean getnewinput = true;
		String returnstr="";
		List<Map> list = new ArrayList<Map>();
		int resultnum=0;
		while(getnewinput){
			if (inputList != null) {
				getnewinput = false;
				for (int i = 0; i< rulesList.size(); i++) {
					//������е�˳λ����
					Rule rule = rulesList.get(i);
					//����ۺ����ݿ��п���ƥ�䵽�ù���
					if (inputList.containsAll(rule.getmConditions())){
						
						 JSONObject jo = new JSONObject();

						 Map<String, String> map = new HashMap<String, String>();
					     map.put("conditions", rule.getmConditionsStr());
					     map.put("result", rule.getmResult());
					     list.add(map);
						String result = rule.getmResult();
						//��������ս�
						if (resultbase.contains(result)) {
							JSONArray jay = JSONArray.fromObject(list);
							returnstr = jay.toString();
							rulesList.remove(rule);
							resultnum++;
							System.out.println(rulesList.size()+"dada");
							i--;
							//return jay.toString();
						}else if(!inputList.contains(result)){
							inputList.add(result);
							rulesList.remove(rule);
							System.out.println(rulesList.size()+"kaka");
							i--;
							getnewinput = true;
						}
						
					}
				}
			}
		}
		System.out.println("resultnum="+ resultnum);
		if(resultnum>1)
			returnstr="2";
		else if(resultnum==0)
			returnstr="0";
		
		return returnstr;
	}
	

}
