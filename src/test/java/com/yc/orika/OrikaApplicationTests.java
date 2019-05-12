package com.yc.orika;


import com.alibaba.fastjson.JSON;
import com.yc.orika.convert.BooleanConfigConvert;
import com.yc.orika.convert.DateConfigConvert;
import com.yc.orika.pojo.FirstA;
import com.yc.orika.pojo.FirstB;
import com.yc.orika.pojo.FiveA;
import com.yc.orika.pojo.FiveB;
import com.yc.orika.pojo.FourA;
import com.yc.orika.pojo.FourB;
import com.yc.orika.pojo.SecondA;
import com.yc.orika.pojo.SecondB;
import com.yc.orika.pojo.ThirdA;
import com.yc.orika.pojo.ThirdB;
import com.yc.orika.pojo.ThirdComm;
import com.yc.orika.pojo.ThirdCommA;
import com.yc.orika.pojo.ThirdCommB;
import com.yc.orika.util.OrikaUtil;
import com.yc.orika.util.pojo.OrikaVo;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrikaApplicationTests {

    //测试不同名字匹配和 不配置的字段是否会复制(在不需要全部复制时候，可以选择false)
    @Test
    public void first() {
        //创建实体
        FirstA firstA=new FirstA(100L,"name",18,10.0,new BigDecimal(60),new Byte("2"),true);
        FirstB firstB=new FirstB();
        //配置属性
        Map<String,String> map=new HashMap<String, String>();
        map.put("price","priceB");
        map.put("amount","amountB");
        map.put("type","typeB");
        map.put("flag","flagB");
        OrikaVo orikaVo=new OrikaVo();
        orikaVo.setFlag(true);
        //orikaVo.setFlag(false);
        orikaVo.setMap(map);
        orikaVo.setSource(FirstA.class);
        orikaVo.setTarget(FirstB.class);
        firstB=(FirstB)OrikaUtil.orikaSignalOnePoConfigure(orikaVo,firstA,firstB,null);
        System.out.println(JSON.toJSONString(firstB));
    }


    //测试属性是否可以相互转换 并且可以自定义适配器
    //原本不支持boolean转换int 或者其他 所以可以自定义适配器去适配
    // 此处只写2个  date是有的默认返回"Wed Mar 20 16:58:24 CST 2019"格式
    //适配器的BidirectionalConverter<Boolean,Integer> 2个参数也可以指定的类 这样用户可以自己定制
    @Test
    public void sencond() {
        //创建实体
        SecondA secondA=new SecondA(100,"name",18L,10.0,new BigDecimal(60),new Byte("2"),true,new Date());
        SecondB secondB=new SecondB();
        //配置属性
        OrikaVo orikaVo=new OrikaVo();
        orikaVo.setFlag(true);
        orikaVo.setSource(SecondA.class);
        orikaVo.setTarget(SecondB.class);
        //自定义适配器
        List<Class<? extends BidirectionalConverter>> classes =new ArrayList<Class<? extends BidirectionalConverter>>();
        classes.add(DateConfigConvert.class);
        classes.add(BooleanConfigConvert.class);
        //执行
        secondB=(SecondB)OrikaUtil.orikaSignalOnePoConfigure(orikaVo,secondA,secondB,classes);
        System.out.println(JSON.toJSONString(secondB));
    }

    //测试实体中套实体并映射实体
    //此测试数据多种配置映射  需要用OrikaUtil.orikaSignalMorePoConfigure方法
    @Test
    public void third() {
        //创建实体
        ThirdComm comm=new ThirdComm(100L,"name");
        ThirdCommA commA=new ThirdCommA(101L,"name1");
        ThirdA thirdA=new ThirdA(comm,commA);
        ThirdB thirdB=new ThirdB();
        //配置属性1
        Map<String,String> map=new HashMap<String, String>();
        map.put("commA","commB");
        OrikaVo orikaVo=new OrikaVo();
        orikaVo.setFlag(true);
        orikaVo.setMap(map);
        orikaVo.setSource(ThirdA.class);
        orikaVo.setTarget(ThirdB.class);
        //配置属性2
        Map<String,String> map2=new HashMap<String, String>();
        map2.put("id","idB");
        map2.put("name","nameB");
        OrikaVo orikaVo2=new OrikaVo();
        orikaVo2.setFlag(true);
        orikaVo2.setMap(map2);
        orikaVo2.setSource(ThirdCommA.class);
        orikaVo2.setTarget(ThirdCommB.class);
        //装入list
        List<OrikaVo> list=new ArrayList<OrikaVo>();
        list.add(orikaVo);
        list.add(orikaVo2);
        //执行
        ThirdB rs=(ThirdB)OrikaUtil.orikaSignalMorePoConfigure(list,thirdA,thirdB,null);

        System.out.println(JSON.toJSONString(rs));
    }


    //测试实体中套实体但是映射的实体是字段

    @Test
    public void four() {
        //创建实体
        ThirdComm comm=new ThirdComm(100L,"name");

        FourA fourA=new FourA(comm);
        FourB fourB=new FourB();
        //配置属性
        Map<String,String> map=new HashMap<String, String>();
        map.put("comm.id","id");
        map.put("comm.name","name");
        OrikaVo orikaVo=new OrikaVo();
        orikaVo.setFlag(true);
        orikaVo.setMap(map);
        orikaVo.setSource(FourA.class);
        orikaVo.setTarget(FourB.class);
        //执行
        FourB rs=(FourB)OrikaUtil.orikaSignalOnePoConfigure(orikaVo,fourA,fourB,null);

        System.out.println(JSON.toJSONString(rs));
    }

    //list<FiveA>直接转化List<FiveB>的情况 如果FiveA和B内部还需要配置 ,则需要多配置几个orikaVo
    //调用OrikaUtil.orikaListMorePoConfigure
    @Test
    public void five() {
        //创建实体
        FiveA fiveA=new FiveA(100L,"name");
        List<FiveA> fiveAList=new ArrayList<FiveA>();
        fiveAList.add(fiveA);
        fiveAList.add(fiveA);
        fiveAList.add(fiveA);
        List<FiveB> fiveBList=new ArrayList<FiveB>();
        //配置属性
        Map<String,String> map=new HashMap<String, String>();
        map.put("id","idB");
        map.put("name","nameB");
        OrikaVo orikaVo=new OrikaVo();
        orikaVo.setFlag(true);
        orikaVo.setMap(map);
        orikaVo.setSource(FiveA.class);
        orikaVo.setTarget(FiveB.class);
        fiveBList=(List<FiveB>)OrikaUtil.orikaListOnePoConfigure(orikaVo,fiveAList,new FiveB(),null);
        for (FiveB fiveB : fiveBList) {
            System.out.println(JSON.toJSONString(fiveB));
        }

    }
}
