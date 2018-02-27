/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.cms.web.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.solr.SpringSolr;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.cms.entity.Article;
import com.scnjwh.intellectreport.modules.cms.entity.ArticleData;
import com.scnjwh.intellectreport.modules.cms.entity.Guestbook;
import com.scnjwh.intellectreport.modules.cms.entity.Site;
import com.scnjwh.intellectreport.modules.cms.service.ArticleService;
import com.scnjwh.intellectreport.modules.cms.service.GuestbookService;
import com.scnjwh.intellectreport.modules.cms.utils.CmsUtils;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 网站搜索Controller
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/search")
public class FrontSearchController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private GuestbookService guestbookService;
	@Autowired
	private SpringSolr solr;
	/**
	 * 全站搜索
	 */
	@RequestMapping(value = "")
	public String search(String content,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		long start = System.currentTimeMillis();
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		try {
			String no = request.getParameter("pageNo");
			Page p = new Page(request, response);
			if(no==null || no.equals("1")){
				p.setPageNo(0);
			}
			Page<Map<String, String>> page = solr.query(p, content);
			List<Article> li = new ArrayList<>();
			for(Map<String , String> map :page.getList()){
				String a =StringUtils.strip(map.get("title"),"[]");
				String b = StringUtils.strip(map.get("content"),"[]");
				String c = map.get("id");
				Article o = articleService.get(c);
				o.setTitle(a);
			    ArticleData articleData = new ArticleData();
			    articleData.setContent(b);
				o.setArticleData(articleData);
				li.add(o);
			}
			Page<Article> ap = new Page<>(page.getPageNo(), page.getPageSize(), page.getCount(), li);
//			page.setList(list);
			model.addAttribute("page", ap);
			model.addAttribute("content", content);
			System.out.println(page.getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		// 重建索引（需要超级管理员权限）
//		if ("cmd:reindex".equals(q)) {
//			if (UserUtils.getUser().isAdmin()) {
//				// 文章模型
//				if (StringUtils.isBlank(t) || "article".equals(t)) {
//					articleService.createIndex();
//				}
//				// 留言模型
//				else if ("guestbook".equals(t)) {
//					guestbookService.createIndex();
//				}
//				model.addAttribute("message","重建索引成功，共耗时 " + (System.currentTimeMillis() - start)+ "毫秒。");
//			} else {
//				model.addAttribute("message", "你没有执行权限。");
//			}
//		}
//		// 执行检索
//		else {
//			String qStr = StringUtils.replace(StringUtils.replace(q, "，", " "),", ", " ");
//			// 如果是高级搜索
//			if ("1".equals(a)) {
//				if (StringUtils.isNotBlank(qand)) {
//					qStr += " +"+ StringUtils.replace(StringUtils.replace(StringUtils.replace(qand, "，", " "), ", "," "), " ", " +");
//				}
//				if (StringUtils.isNotBlank(qnot)) {
//					qStr += " -"+ StringUtils.replace(StringUtils.replace(StringUtils.replace(qnot, "，", " "), ", "," "), " ", " -");
//				}
//			}
//			// 文章检索
//			if (StringUtils.isBlank(t) || "article".equals(t)) {
//				Page<Article> page = articleService.search(new Page<Article>(request, response), qStr, cid, bd, ed);
//				page.setMessage("匹配结果，共耗时 "+ (System.currentTimeMillis() - start) + "毫秒。");
//				model.addAttribute("page", page);
//			}
//			// 留言检索
//			else if ("guestbook".equals(t)) {
//				Page<Guestbook> page = guestbookService.search(new Page<Guestbook>(request, response), qStr, bd, ed);
//				page.setMessage("匹配结果，共耗时 "+ (System.currentTimeMillis() - start) + "毫秒。");
//				model.addAttribute("page", page);
//			}
//
//		}
//		model.addAttribute("t", t);// 搜索类型
//		model.addAttribute("q", q);// 搜索关键字
//		model.addAttribute("qand", qand);// 包含以下全部的关键词
//		model.addAttribute("qnot", qnot);// 不包含以下关键词
//		model.addAttribute("cid", cid);// 搜索类型
		return "modules/cms/front/themes/" + site.getTheme() + "/frontSearch";
	}

}
