package com.wanghong.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wanghong.po.Actor;
import com.wanghong.po.ActorPayVideo;
import com.wanghong.po.DataDictionary;
import com.wanghong.po.Message;
import com.wanghong.po.Status;
import com.wanghong.service.ActorPayVideoService;
import com.wanghong.service.ActorService;
import com.wanghong.util.LogFactory;

@Controller
@RequestMapping("/actor")
public class ActorController extends BaseAction {

	@Autowired
	private ActorService actorService;
	@Autowired
	private ActorPayVideoService actorPayVideoService;

	@RequestMapping(value = "actor")
	public String actor() {
		request.setAttribute("menu", "actor");
		return "actor/actor";
	}

	@RequestMapping(value = "getInfo")
	public String getInfo() throws Exception {
		request.setAttribute("menu", "actor");
		/**
		 * 从session中获取信息，并根据权限get用户
		 */
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Actor actor = actorService.getById(Integer.parseInt(id));
			request.setAttribute("actor", actor);
		}
		return "actor/actorInfo";
	}

	@RequestMapping(value = "add")
	public String add() {
		request.setAttribute("menu", "actor");
		return "actor/actorAdd";
	}

	@RequestMapping("getActorList")
	@ResponseBody
	public void getList() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// 用于添加查询条件的map
		groupSortMap(model);

		model.put("name", StringUtils.isNotBlank(request.getParameter("name")) ? request.getParameter("name") : null);
		model.put("nickname",
				StringUtils.isNotBlank(request.getParameter("nickname")) ? request.getParameter("nickname") : null);
		model.put("phone",
				StringUtils.isNotBlank(request.getParameter("phone")) ? request.getParameter("phone") : null);
		model.put("state",
				StringUtils.isNotBlank(request.getParameter("state")) ? request.getParameter("state") : null);
		model.put("startTime",
				StringUtils.isNotBlank(request.getParameter("startTime")) ? request.getParameter("startTime") : null);
		model.put("endTime",
				StringUtils.isNotBlank(request.getParameter("endTime")) ? request.getParameter("endTime") : null);

		// 根据查询条件查询的的数据信息并获取数据的总量
		int count = actorService.count(model);
		recordsTotal = count;
		// 分页显示上面查询出的数据结果
		List<Actor> data = actorService.getListByMap(model);
		recordsFiltered = recordsTotal;
		recordsDisplay = data.size();
		this.writerToClient(data, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
	}

	@RequestMapping("getActorVideo")
	public void getActorVideo() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("startPage", getStart());
		model.put("pageSize", getIDisplayLength());
		model.put("actorId", request.getParameter("actorId"));
		model.put("type", 1);
		int count = actorPayVideoService.count(model);
		recordsTotal = count;
		String imgPath = getImgFilePath();
		// 分页显示上面查询出的数据结果
		List<ActorPayVideo> data = actorPayVideoService.getListByMap(model);
		List<ActorPayVideo> data1 = new ArrayList<ActorPayVideo>();

		for (ActorPayVideo apv : data) {
			ActorPayVideo apv1 = apv;
			apv1.setImage("../downloadFile.jsp?identity=" + imgPath + "&path=" + apv.getImage());
			data1.add(apv1);
		}
		recordsFiltered = recordsTotal;
		recordsDisplay = data1.size();
		this.writerToClient(data1, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
	}

	@RequestMapping("addActorVideo")
	public void addActorVideo() throws Exception {
		ActorPayVideo apv = new ActorPayVideo();
		String savePath = getImgFilePath();
		try {
			Map<String, Object> uploadResultMap = fileUpload(savePath, "image");
			String icon = (String) uploadResultMap.get("filePath");
			if (StringUtils.isBlank(icon)) {
				throw new Exception("视频截图上传失败");
			}
			apv.setImage(icon);
		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}

		try {
			Map<String, Object> uploadResultMap = fileUpload(savePath, "video");
			String icon = (String) uploadResultMap.get("filePath");
			if (StringUtils.isBlank(icon)) {
				throw new Exception("视频上传失败");
			}
			apv.setSavePath(icon);
		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}

		try {
			apv.setActorId(Integer.parseInt(request.getParameter("actorId")));
			apv.setIntroduction(request.getParameter("introduction"));
			apv.setType(1);
			apv.setPrice(Integer.parseInt(request.getParameter("price")));
			apv.setStatus(Integer.parseInt(request.getParameter("status")));
			apv.setCreateTime(new Date());
			actorPayVideoService.insert(apv);
			setJsonSuccess(response, null, "添加成功", RESULT_TYPE_CLOSE_BOX_FUNCTION);
		} catch (Exception e) {
			LogFactory.getInstance().getLogger().error("添加主播视频信息失败：", e);
			setJsonFail(response, null, 1100, "添加主播信息失败！");
		}
	}
	
	@RequestMapping("editActorVideo")
	public void editActorVideo() throws Exception {
		ActorPayVideo apv = new ActorPayVideo();
		String id = request.getParameter("id");
		String savePath = getImgFilePath();
		try {
			Map<String, Object> uploadResultMap = fileUpload(savePath, "image");
			String icon = (String) uploadResultMap.get("filePath");
			apv.setImage(icon);
		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}

		try {
			Map<String, Object> uploadResultMap = fileUpload(savePath, "video");
			String icon = (String) uploadResultMap.get("filePath");
			apv.setSavePath(icon);
		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}

		try {
			apv.setId(Integer.parseInt(id));
			apv.setActorId(Integer.parseInt(request.getParameter("actorId")));
			apv.setIntroduction(request.getParameter("introduction"));
			apv.setType(1);
			apv.setPrice(Integer.parseInt(request.getParameter("price")));
			apv.setStatus(Integer.parseInt(request.getParameter("status")));
			apv.setCreateTime(new Date());
			actorPayVideoService.update(apv);
			setJsonSuccess(response, null, "修改成功", RESULT_TYPE_CLOSE_BOX_FUNCTION);
		} catch (Exception e) {
			LogFactory.getInstance().getLogger().error("修改主播视频信息失败：", e);
			setJsonFail(response, null, 1100, "修改主播信息失败！");
		}
	}

	@RequestMapping("editVideo")
	public void editVideo() throws Exception {
		String videoId = request.getParameter("id");
		ActorPayVideo apv = actorPayVideoService.getById(Integer.parseInt(videoId));
		String image = apv.getImage();
		String savePath = apv.getSavePath();
		String filePath = getImgFilePath();
		if (StringUtils.isNotBlank(image)) {
			apv.setImage("../downloadFile.jsp?identity=" + filePath + "&path=" + image);
		}
		if(StringUtils.isNotBlank(savePath)){
			apv.setSavePath("../downloadFile.jsp?identity=" + filePath + "&path=" + savePath);
		}
		Gson g = new GsonBuilder().serializeNulls().create();
		this.writerToClient(g.toJson(apv));
	}

	@RequestMapping("addActor")
	public void addActor() throws Exception {
		Actor actor = new Actor();
		try {
			DataDictionary dictionary = dictionaryService.getDicByKey("file_save_path");
			String serverSavePath = dictionary.getValue();
			Map<String, Object> uploadResultMap = fileUpload(serverSavePath, "icon");
			String icon = (String) uploadResultMap.get("filePath");
			if (StringUtils.isBlank(icon)) {
				throw new Exception("图片上传失败");
			}
			actor.setIcon(icon);
		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}
		try {
			actor.setName(request.getParameter("name"));
			actor.setNickname(request.getParameter("nickname"));
			actor.setPhone(request.getParameter("phone"));
			actor.setPassword(request.getParameter("password"));
			actor.setSignature(request.getParameter("signature"));// 个性签名
			actor.setIntroduction(request.getParameter("introduction"));// 自我介绍
			actor.setState(Integer.parseInt(request.getParameter("state")));
			actor.setSex(StringUtils.isNotBlank(request.getParameter("sex"))
					? Integer.parseInt(request.getParameter("sex")) : 2);
			actor.setAge(StringUtils.isNotBlank(request.getParameter("age"))
					? Integer.parseInt(request.getParameter("age")) : 21);
			actor.setProvice(request.getParameter("provice"));
			actor.setCity(request.getParameter("city"));
			actor.setChannel(request.getParameter("channel"));
			actor.setPrice(StringUtils.isNotBlank(request.getParameter("price"))
					? Integer.parseInt(request.getParameter("price")) : 0);
			actor.setFenchengbi(StringUtils.isNotBlank(request.getParameter("fenchengbi"))
					? Integer.parseInt(request.getParameter("fenchengbi")) : 0);
			actor.setBankAccount(request.getParameter("bankAccount"));
			actor.setRemarks(request.getParameter("remarks"));
			actor.setQq(request.getParameter("qq"));
			actor.setWechat(request.getParameter("wechat"));
			actor.setCreator(request.getParameter("creator"));
			actor.setCreateTime(new Date());
			actorService.insert(actor);
			setJsonSuccess(response, null, "添加成功", RESULT_TYPE_CLOSE_BOX_FUNCTION);
		} catch (Exception e) {
			LogFactory.getInstance().getLogger().error("添加主播信息失败：", e);
			setJsonFail(response, null, 1100, "添加主播信息失败,请检查添加信息是否重复！");
		}
	}

	@RequestMapping("edit")
	public String edit() throws Exception {
		request.setAttribute("menu", "actor");
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Actor actor = actorService.getById(Integer.parseInt(id));
			request.setAttribute("actor", actor);
			request.setAttribute("imageHref",
					"../downloadFile.jsp?identity=" + getImgFilePath() + "&path=" + actor.getIcon());
		}
		return "actor/actorEdit";
	}

	@RequestMapping("addPhoto")
	public String addPhoto() throws Exception {
		request.setAttribute("menu", "actor");
		String id = request.getParameter("id");
		request.setAttribute("actorId", id);
		return "actor/actorPhotoAdd";
	}

	@RequestMapping("actorPhoto")
	public String actorPhoto() throws Exception {
		request.setAttribute("menu", "actor");
		String id = request.getParameter("id");
		request.setAttribute("actorId", id);
		return "actor/actorPhoto";
	}

	@RequestMapping("addVideo")
	public String addVideo() throws Exception {
		request.setAttribute("menu", "actor");
		String id = request.getParameter("id");
		request.setAttribute("actorId", id);
		return "actor/actorVideo";
	}

	@RequestMapping("actorPV")
	public void actorPV() throws Exception {
		String id = request.getParameter("actorId");
		ActorPayVideo apv = new ActorPayVideo();
		apv.setActorId(Integer.parseInt(id));
		apv.setType(2);
		List<ActorPayVideo> apvList = actorPayVideoService.getListByPo(apv);
		Map<String, Object> apvMap = new HashMap<String, Object>();
		String imgPath = getImgFilePath();
		apvMap.put("ap", apvList);
		apvMap.put("imgPath", imgPath);
		Gson g = new GsonBuilder().serializeNulls().create();
		this.writerToClient(g.toJson(apvMap));
	}

	@RequestMapping("editActor")
	public void editActor() throws Exception {
		Actor actor = new Actor();
		try {
			DataDictionary dictionary = dictionaryService.getDicByKey("file_save_path");
			String serverSavePath = dictionary.getValue();
			Map<String, Object> uploadResultMap = fileUpload(serverSavePath, "icon");
			String icon = (String) uploadResultMap.get("filePath");
			if (!StringUtils.isBlank(icon)) {
				actor.setIcon(icon);
				// throw new Exception("图片上传失败");
			}

		} catch (Exception e) {
			setJsonFail(response, null, 1100, e.getMessage());
			return;
		}
		try {
			actor.setId(Integer.parseInt(request.getParameter("id")));
			actor.setName(request.getParameter("name"));
			actor.setNickname(request.getParameter("nickname"));
			actor.setPhone(request.getParameter("phone"));
			actor.setPassword(request.getParameter("password"));
			actor.setSignature(request.getParameter("signature"));// 个性签名
			actor.setIntroduction(request.getParameter("introduction"));// 自我介绍
			actor.setState(Integer.parseInt(request.getParameter("state")));
			actor.setSex(StringUtils.isNotBlank(request.getParameter("sex"))
					? Integer.parseInt(request.getParameter("sex")) : 2);
			actor.setAge(StringUtils.isNotBlank(request.getParameter("age"))
					? Integer.parseInt(request.getParameter("age")) : 21);
			actor.setProvice(request.getParameter("provice"));
			actor.setCity(request.getParameter("city"));
			actor.setChannel(request.getParameter("channel"));
			actor.setPrice(StringUtils.isNotBlank(request.getParameter("price"))
					? Integer.parseInt(request.getParameter("price")) : 0);
			actor.setFenchengbi(StringUtils.isNotBlank(request.getParameter("fenchengbi"))
					? Integer.parseInt(request.getParameter("fenchengbi")) : 0);
			actor.setBankAccount(request.getParameter("bankAccount"));
			actor.setRemarks(request.getParameter("remarks"));
			actor.setQq(request.getParameter("qq"));
			actor.setWechat(request.getParameter("wechat"));
			LogFactory.getInstance().getLogger().error("更新信息：" + actor);
			actorService.update(actor);
			setJsonSuccess(response, null, "更新成功", RESULT_TYPE_CLOSE_BOX_FUNCTION);
		} catch (Exception e) {
			LogFactory.getInstance().getLogger().error("系统错误：" + e.getMessage());
			setJsonFail(response, null, 1100, "修改失败,请检查修改信息是否重复！");
		}
	}

	@RequestMapping("onlineActor")
	public void onlineActor() throws Exception {
		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			Integer state = StringUtils.isNotBlank(request.getParameter("state"))
					? Integer.parseInt(request.getParameter("state")) : -1;
			if (state == 0)
				state = 1;
			else
				state = 0;
			Actor actor = new Actor();
			actor.setId(id);
			actor.setState(state);
			actorService.update(actor);
			this.writerToClient("操作成功");// 成功
		} catch (Exception e) {
			LogFactory.getInstance().getLogger().error("无法获取id或状态，也可能是更新错误", e);
			this.writerToClient("服务器错误");// 成功
		}
	}

	@RequestMapping(value = "uploadPhoto", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public void uploadPhoto(@RequestParam(value = "actorPhoto", required = false) MultipartFile[] files)
			throws Exception {
		// 缓存当前的文件
		String actorId = request.getParameter("actorId");
		String fileName = null;
		String relativePath = null;
		Message msg = new Message();
		try {
			MultipartFile file = files[0];
			String prefix = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 父目录

			File dir = new File(getImgFilePath() + File.separator + prefix);
			if (!dir.exists())
				dir.mkdirs();

			fileName = file.getOriginalFilename(); // 文件名
			relativePath = prefix + File.separator + fileName; // 文件相对路径
			File saveFile = new File(dir.getAbsoluteFile() + File.separator + fileName);

			file.transferTo(saveFile);// 将文件写入到服务器
			msg.setStatus(Status.SUCCESS);
			msg.setFileName(fileName);
			msg.setFilePath(relativePath);
			ActorPayVideo apv = new ActorPayVideo();
			apv.setActorId(Integer.parseInt(actorId));
			apv.setType(2);
			apv.setSavePath(relativePath);
			apv.setStatus(1);
			apv.setCreateTime(new Date());
			actorPayVideoService.insert(apv);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setStatus(Status.ERROR);
			msg.setFileName(fileName);
			msg.setStatusMsg(fileName + "文件上传失败");
		}

		Gson g = new GsonBuilder().serializeNulls().create();
		this.writerToClient(g.toJson(msg));
	}
	
	
	@RequestMapping(value="updatePhotoState")
	public void updatePhotoState() throws Exception{
		String apId = request.getParameter("apId");
		ActorPayVideo apv = actorPayVideoService.getById(Integer.parseInt(apId));
		if(apv.getStatus() == 1){
			apv.setStatus(0);
		}else{
			apv.setStatus(1);
		}
		actorPayVideoService.update(apv);
	}
	
	@RequestMapping(value="actorInfo")
	public void actorInfo() throws Exception{
		String actorId = request.getParameter("actorId");
		Map<String,Object> actorInfoMap = new HashMap<String,Object>();
		try {
			String filePath = getImgFilePath();
			Actor actor = actorService.getById(Integer.parseInt(actorId));
			ActorPayVideo apv = new ActorPayVideo();
			apv.setActorId(Integer.parseInt(actorId));
			apv.setStatus(1);
			List<ActorPayVideo> apvList = actorPayVideoService.getListByPo(apv);
			List<ActorPayVideo> apList = new ArrayList<ActorPayVideo>();
			List<ActorPayVideo> avList = new ArrayList<ActorPayVideo>();
			for(ActorPayVideo actorPayVideo : apvList){
				if(actorPayVideo.getType() == 2){ // 相册
					apList.add(actorPayVideo);
				}else if(actorPayVideo.getType() == 1){ // 视频
					avList.add(actorPayVideo);
				}
			}
			
			actorInfoMap.put("actorBaseInfo", actor);
			actorInfoMap.put("ap", apList);
			actorInfoMap.put("av", avList);
			actorInfoMap.put("filePath", filePath);
			Gson g = new GsonBuilder().serializeNulls().create();
			this.writerToClient(g.toJson(actorInfoMap));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
