//package com.wanghong.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.wanghong.po.DataDictionary;
//import com.wanghong.util.LogFactory;
//
//@Controller
//@RequestMapping("/dictionary")
//public class DictionaryController extends BaseAction {
//
//	/**
//	 * 2016年9月20日
//	 * getList
//	 * 方法描述: 获取视频列表
//	 * 逻辑描述:
//	 * @throws Exception
//	 */
//	@RequestMapping("getList")
//	@ResponseBody
//	public void getList() throws Exception {
//		Map<String,Object> model = new HashMap<String,Object>();
//		// 用于添加查询条件的map
//		groupSortMap(model);
//		model.put("name", StringUtils.isNotBlank(request.getParameter("name")) ? request.getParameter("name") : null);
//		// 根据查询条件查询的的数据信息并获取数据的总量
//		int count = dictionaryService.count(model);
//		recordsTotal = count;
//		// 分页显示上面查询出的数据结果
//		List<DataDictionary> data = dictionaryService.loadList(model);
//		recordsFiltered = recordsTotal;
//		recordsDisplay = data.size();
//		this.writerToClient(data, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
//	}
//
//	/**
//	 * 
//	 * isExistsKey
//	 * 方法描述: 是否存在Key
//	 * 逻辑描述: 分两种情况
//	 * 1：添加的时候需要判断是否存在Key值，如果存在则返回key已存在
//	 * 2：修改的时候直接跳过，不判断key值
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("isExistsKey")
//	public void isExistsKey() throws Exception {
//		Map<String, Object> conditionMap = new HashMap<String, Object>();
//		String id = request.getParameter("id");
//		conditionMap.put("id", id != null && !"".equals(id) ? id : null);
//		conditionMap.put("name", request.getParameter("name"));
//		int count = dictionaryService.countKey(conditionMap);
//		if(count == 0) {
//			this.writerToClient("true");
//		}
//		else {
//			this.writerToClient("false");
//		}
//	}
//	
//	/**
//	 * 
//	 * addDictionary
//	 * 方法描述: 添加数据字典
//	 * 逻辑描述:
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("addDictionary")
//	public void addDictionary(DataDictionary dictionary) throws Exception {
//		try {
//			dictionary.setCreateTime(new Date());
//			dictionary.setUpdateTime(new Date());
//			dictionaryService.insert(dictionary);
//			setJsonSuccess(response, null, "添加成功",RESULT_TYPE_CLOSE_BOX_FUNCTION);
//		}
//		catch(Exception e) {
//			LogFactory.getInstance().getVideoContentManagerLogger().error("系统错误：" + e.getMessage());
//			setJsonFail(response, null, 1100, "添加失败,请检查添加信息是否重复！");
//		}
//	}
//	/**
//	 * 
//	 * addDictionary
//	 * 方法描述: 添加数据字典
//	 * 逻辑描述:
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("editDictionary")
//	public void editDictionary(DataDictionary dictionary) throws Exception {
//		try {
//			dictionary.setUpdateTime(new Date());
//			dictionaryService.update(dictionary);
//			setJsonSuccess(response, null, "修改成功",RESULT_TYPE_CLOSE_BOX_FUNCTION);
//		}
//		catch(Exception e) {
//			LogFactory.getInstance().getVideoContentManagerLogger().error("系统错误：" + e.getMessage());
//			setJsonFail(response, null, 1100, "修改失败,请检查修改信息是否重复！");
//		}
//	}
//	
//	/**
//	 * 
//	 * deleteDictionary 方法描述: 删除数据字典 逻辑描述:
//	 * 
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("delete")
//	public void delete() throws Exception {
//		try {
//			Integer id = Integer.valueOf(request.getParameter("id"));
//			dictionaryService.deleteById(id);
//			this.writerToClient("操作成功");// 成功
//		}
//		catch(Exception e) {
//			LogFactory.getInstance().getVideoContentManagerLogger().error("数据错误", e);
//			this.writerToClient("其他错误");// 成功
//		}
//	}
//	
//	/**
//	 * 
//	 * getDictionaryById
//	 * 方法描述: 通过ID获取数据字典对象
//	 * 逻辑描述:
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("getDictionaryById")
//	public void getDictionaryById() throws Exception {
//		Integer id = Integer.valueOf(request.getParameter("id"));
//		DataDictionary dataDictionary = dictionaryService.selectById(id);
//
//		if(dataDictionary != null) {
//			Gson g = new GsonBuilder().serializeNulls().create();
//			this.writerToClient(g.toJson(dataDictionary));
//		}
//	}
//	/**
//	 * 
//	 * getDicByKey
//	 * 方法描述: 构建页面select下拉框信息
//	 * 逻辑描述:
//	 * @throws Exception
//	 * @since Ver 1.00
//	 */
//	@RequestMapping("getDicByKey")
//	public void getDicByKey() throws Exception {
//		String key = request.getParameter("key");
//		String value = request.getParameter("value");
//		DataDictionary data = new DataDictionary();
//		data.setName(key);
//		DataDictionary dictionary = dictionaryService.getDicByKey(key);
//
//		if(dictionary != null) {
//			String values = dictionary.getValue();
//			String[] content = values.split(";");
//			Gson g = new GsonBuilder().serializeNulls().create();
//			this.writerToClient(g.toJson(constructOption(content, value)));
//		}
//		else {
//			this.writerToClient("empty");
//		}
//	}
//	
//	/**
//	 * 
//	 * constructOption
//	 * 方法描述: 构建前台的select中的Option项
//	 * 逻辑描述: 如果存在项中键值=value的则做成是默认选项
//	 * @param content
//	 * @param value
//	 * @return
//	 * @since Ver 1.00
//	 */
//	public List<SelectGroup> constructOption(String[] content, String value) {
//		List<SelectGroup> groupList = new ArrayList<SelectGroup>();
//		for(String con : content) {
//			String[] kv = con.split("=");
//			SelectGroup group = new SelectGroup();
//			group.setId(kv[0]);
//			group.setName(kv[1]);
//			groupList.add(group);
//		}
//		return groupList;
//	}
//	
//	@RequestMapping("dictionary")
//	public String list(ModelMap model) throws Exception {
//		request.setAttribute("menu", "dictionary");
//		return "dictionary/dictionary";
//	}
//	
//	@RequestMapping("add")
//	public String add(ModelMap model) throws Exception {
//		request.setAttribute("menu", "dictionary");
//		String id =request.getParameter("id");
//		if(StringUtils.isNotBlank(id)){
//			DataDictionary dataDictionary = dictionaryService.selectById(Integer.parseInt(id));
//
//			request.setAttribute("dic", dataDictionary);
//		}
//		return "dictionary/dictionaryAdd";
//	}
//}
