package com.wanghong.test.junit;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wanghong.po.Actor;
import com.wanghong.service.ActorService;





@FixMethodOrder(MethodSorters.NAME_ASCENDING) //按字母顺序执行method
public class ActorServiceImplTest extends BaseTest {
	@Autowired
	ActorService actorService;
	
	@Test
	public void a_insertTest() throws SQLException {
		Actor po = new Actor();
	  	po.setName("Name");
	  	po.setNickname("Nickname");
	  	po.setIcon("Icon");
	  	po.setSignature("Signature");
	  	po.setIntroduction("Introduction");
	  	po.setPrice(1);
	  	po.setState(1);
	  	po.setSex(1);
	  	po.setAge(11);
	  	po.setProvice("Provice");
	  	po.setCity("City");
	  	po.setPhone("Phone");
	  	po.setPassword("Password");
	  	po.setChannel("Channel");
	  	po.setFenchengbi(1);
	  	po.setBankAccount("BankAccount");
	  	po.setRemarks("Remarks");
	  	po.setQq("Qq");
	  	po.setWechat("Wechat");
	  	po.setCreator("Creator");
	  	po.setCreateTime(new java.util.Date(System.currentTimeMillis()));
	  	po.setUpdateTime(new java.util.Date(System.currentTimeMillis()));
		int result = actorService.insert(po);
		System.out.println("insert result = " + result);
	}

	@Test
	public void b_updateTest() throws SQLException {
		Actor po = new Actor();
	  	po.setId(1);
	  	po.setName("Name_update");
	  	po.setNickname("Nickname_update");
	  	po.setIcon("Icon_update");
	  	po.setSignature("Signature_update");
	  	po.setIntroduction("Introduction_update");
	  	po.setPrice(1);
	  	po.setState(1);
	  	po.setSex(1);
	  	po.setAge(1);
	  	po.setProvice("Provice_update");
	  	po.setCity("City_update");
	  	po.setPhone("Phone_update");
	  	po.setPassword("Password_update");
	  	po.setChannel("Channel_update");
	  	po.setFenchengbi(1);
	  	po.setBankAccount("BankAccount_update");
	  	po.setRemarks("Remarks_update");
	  	po.setQq("Qq_update");
	  	po.setWechat("Wechat_update");
	  	po.setCreator("Creator_update");
	  	po.setCreateTime(new java.util.Date());
	  	po.setUpdateTime(new java.util.Date());
		int result = actorService.update(po);
		System.out.println("update result = " + result);		
	}


	@Test
	public void c_getByIdTest() throws SQLException {
		Actor po = actorService.getById(1);
		System.out.println("getByIdTest result = " + po);
	}

	@Test
	public void d_getListByMapTest() throws SQLException {
		Map<String, Object> condition = new HashMap<String, Object>();
	  	condition.put("id", 1);
	  	condition.put("name", "name");
	  	condition.put("nickname", "nickname");
	  	condition.put("icon", "icon");
	  	condition.put("signature", "signature");
	  	condition.put("introduction", "introduction");
	  	condition.put("price", 1);
	  	condition.put("state", 1);
	  	condition.put("sex", 1);
	  	condition.put("birthday", new java.util.Date());
	  	condition.put("provice", "provice");
	  	condition.put("city", "city");
	  	condition.put("phone", "phone");
	  	condition.put("password", "password");
	  	condition.put("channel", "channel");
	  	condition.put("fenchengbi", 1);
	  	condition.put("bankAccount", "bankAccount");
	  	condition.put("remarks", "remarks");
	  	condition.put("qq", "qq");
	  	condition.put("wechat", "wechat");
	  	condition.put("creator", "creator");
	  	condition.put("createTime", new java.util.Date());
	  	condition.put("updateTime", new java.util.Date());
		List<Actor> result = actorService.getListByMap(condition);
		for(Actor po : result) {
			System.out.println("list:" + po);
		}
	}
	
	@Test
	public void e_getListByPoTest() throws SQLException {
		Actor po = new Actor();
	  	po.setId(1);
	  	po.setName("Name");
	  	po.setNickname("Nickname");
	  	po.setIcon("Icon");
	  	po.setSignature("Signature");
	  	po.setIntroduction("Introduction");
	  	po.setPrice(1);
	  	po.setState(1);
	  	po.setSex(1);
	  	po.setAge(1);
	  	po.setProvice("Provice");
	  	po.setCity("City");
	  	po.setPhone("Phone");
	  	po.setPassword("Password");
	  	po.setChannel("Channel");
	  	po.setFenchengbi(1);
	  	po.setBankAccount("BankAccount");
	  	po.setRemarks("Remarks");
	  	po.setQq("Qq");
	  	po.setWechat("Wechat");
	  	po.setCreator("Creator");
	  	po.setCreateTime(new java.util.Date());
	  	po.setUpdateTime(new java.util.Date());
		List<Actor> result = actorService.getListByPo(po);
		for(Actor obj : result) {
			System.out.println("list:" + obj);
		}		
	}

	@Test
	public void f_countTest() throws SQLException {
		Map<String, Object> condition = new HashMap<String, Object>();
	  	condition.put("id", 1);
	  	condition.put("name", "name");
	  	condition.put("nickname", "nickname");
	  	condition.put("icon", "icon");
	  	condition.put("signature", "signature");
	  	condition.put("introduction", "introduction");
	  	condition.put("price", 1);
	  	condition.put("state", 1);
	  	condition.put("sex", 1);
	  	condition.put("birthday", new java.util.Date());
	  	condition.put("provice", "provice");
	  	condition.put("city", "city");
	  	condition.put("phone", "phone");
	  	condition.put("password", "password");
	  	condition.put("channel", "channel");
	  	condition.put("fenchengbi", 1);
	  	condition.put("bankAccount", "bankAccount");
	  	condition.put("remarks", "remarks");
	  	condition.put("qq", "qq");
	  	condition.put("wechat", "wechat");
	  	condition.put("creator", "creator");
	  	condition.put("createTime", new java.util.Date());
	  	condition.put("updateTime", new java.util.Date());
		int result = actorService.count(condition);
		System.out.println("countTest result = " + result);
	}
	
	@Test
	public void g_deleteByIdTest() throws SQLException {
		int result = actorService.deleteById(1);
		System.out.println("delete result = " + result);
	}
	
}