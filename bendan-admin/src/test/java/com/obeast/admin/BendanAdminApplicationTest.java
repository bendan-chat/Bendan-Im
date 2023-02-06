package com.obeast.admin;

import com.obeast.admin.business.service.AddFriendRelService;
import com.obeast.admin.business.service.BendanMailService;
import com.obeast.business.entity.SysMenuEntity;
import com.obeast.business.entity.SysUserEntity;
import com.obeast.security.business.service.SysMenuService;
import com.obeast.security.business.service.SysUserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 * Date 2022/10/4 0:04
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class BendanAdminApplicationTest {
    @Autowired
    SysUserService sysUserDao;
    @Autowired
    AddFriendRelService addFriendRelService;

    @Autowired
    BendanMailService bendanMailService;




    @Test
    void addFriend() {
         addFriendRelService.delFriendAndChatRecord(1L, 9L);
    }
    @Test
    void bendanMailService() {
        bendanMailService.sendMailCode(1L, "obeast.gym@gmail.com");
    }


    private static final String json = """
            {
                "code": 200,
                "data": [
                    {
                        "icon": "HomeOutlined",
                        "title": "首页",
                        "path": "/home/index"
                    },
                    {
                        "icon": "AreaChartOutlined",
                        "title": "数据大屏",
                        "path": "/dataScreen/index"
                    },
                    {
                        "icon": "TableOutlined",
                        "title": "超级表格",
                        "path": "/proTable",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/proTable/useHooks",
                                "title": "使用 Hooks"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/proTable/useComponent",
                                "title": "使用 Component"
                            }
                        ]
                    },
                    {
                        "icon": "FundOutlined",
                        "title": "Dashboard",
                        "path": "/dashboard",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/dashboard/dataVisualize",
                                "title": "数据可视化"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/dashboard/embedded",
                                "title": "内嵌页面"
                            }
                        ]
                    },
                    {
                        "icon": "FileTextOutlined",
                        "title": "表单 Form",
                        "path": "/form",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/form/basicForm",
                                "title": "基础 Form"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/form/validateForm",
                                "title": "校验 Form"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/form/dynamicForm",
                                "title": "动态 Form"
                            }
                        ]
                    },
                    {
                        "icon": "PieChartOutlined",
                        "title": "Echarts",
                        "path": "/echarts",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/waterChart",
                                "title": "水型图"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/columnChart",
                                "title": "柱状图"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/lineChart",
                                "title": "折线图"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/pieChart",
                                "title": "饼图"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/radarChart",
                                "title": "雷达图"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/echarts/nestedChart",
                                "title": "嵌套环形图"
                            }
                        ]
                    },
                    {
                        "icon": "ShoppingOutlined",
                        "title": "常用组件",
                        "path": "/assembly",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/assembly/guide",
                                "title": "引导页"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/assembly/svgIcon",
                                "title": "Svg 图标"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/assembly/selectIcon",
                                "title": "Icon 选择"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/assembly/batchImport",
                                "title": "批量导入数据"
                            }
                        ]
                    },
                    {
                        "icon": "ProfileOutlined",
                        "title": "菜单嵌套",
                        "path": "/menu",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/menu/menu1",
                                "title": "菜单1"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/menu/menu2",
                                "title": "菜单2",
                                "children": [
                                    {
                                        "icon": "AppstoreOutlined",
                                        "path": "/menu/menu2/menu21",
                                        "title": "菜单2-1"
                                    },
                                    {
                                        "icon": "AppstoreOutlined",
                                        "path": "/menu/menu2/menu22",
                                        "title": "菜单2-2",
                                        "children": [
                                            {
                                                "icon": "AppstoreOutlined",
                                                "path": "/menu/menu2/menu22/menu221",
                                                "title": "菜单2-2-1"
                                            },
                                            {
                                                "icon": "AppstoreOutlined",
                                                "path": "/menu/menu2/menu22/menu222",
                                                "title": "菜单2-2-2"
                                            }
                                        ]
                                    },
                                    {
                                        "icon": "AppstoreOutlined",
                                        "path": "/menu/menu2/menu23",
                                        "title": "菜单2-3"
                                    }
                                ]
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/menu/menu3",
                                "title": "菜单3"
                            }
                        ]
                    },
                    {
                        "icon": "ExclamationCircleOutlined",
                        "title": "错误页面",
                        "path": "/error",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/404",
                                "title": "404页面"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/403",
                                "title": "403页面"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/500",
                                "title": "500页面"
                            }
                        ]
                    },
                    {
                        "icon": "PaperClipOutlined",
                        "title": "外部链接",
                        "path": "/link",
                        "children": [
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/link/gitee",
                                "title": "Gitee 仓库",
                                "isLink": "https://gitee.com/laramie/Hooks-Admin"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/link/github",
                                "title": "GitHub 仓库",
                                "isLink": "https://github.com/HalseySpicy/Hooks-Admin"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/link/juejin",
                                "title": "掘金文档",
                                "isLink": "https://juejin.cn/user/3263814531551816/posts"
                            },
                            {
                                "icon": "AppstoreOutlined",
                                "path": "/link/myBlog",
                                "title": "个人博客",
                                "isLink": "http://www.spicyboy.cn"
                            }
                        ]
                    }
                ],
                "msg": "成功"
            }
            """;


    private static List<SysMenuEntity> getData(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList<SysMenuEntity> list = new ArrayList<>();
        int level = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            SysMenuEntity sysMenuEntity = buildMenu(jsonArray, i, level);
            list.add(sysMenuEntity);
        }
        return list;
    }
    private static List<SysMenuEntity> getChild (JSONObject children) throws JSONException {
        List<SysMenuEntity> list = new ArrayList<>();
        if (children != null){
            JSONObject children2 = children.getJSONObject("children");
            SysMenuEntity sysMenuEntity = new SysMenuEntity();

            if (children2 != null){
                return getChild(children2);
            }
        }
        return list;
    }
    private static SysMenuEntity buildMenu (JSONArray jsonArray, int i, int level) throws JSONException {
        JSONObject obj = jsonArray.getJSONObject(i);
        String icon = obj.getString("icon");
        String path = obj.getString("path");
        String title = obj.getString("title");
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        sysMenuEntity.setIcon(icon);
        sysMenuEntity.setPath(path);
        sysMenuEntity.setName(title);
        sysMenuEntity.setLevel(level);
        return sysMenuEntity;
    }

    public static void main(String[] args) throws JSONException {
        List<SysMenuEntity> data = getData(json);

    }

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    @Test
    void test() throws LoginException, JSONException {
        List<SysMenuEntity> data = getData(json);
//        Long id = bendanSysMenuService.getIdByTitle();
        sysMenuService.saveBatch(data);
    }


    @Test
    void testSaveUser() throws LoginException {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("admin");
        sysUserEntity.setPassword("password");
        sysUserEntity.setEmail("obeast.gym@gmail.com");
    }

    @Test
    void login() {


//        System.out.println(oAuth2TokenRemote.getAccessToken(oAuth2Params));
//        userInfoService.login("user", "password");
    }

}
