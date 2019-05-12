package com.yc.orika.util;

import com.yc.orika.convert.DateConfigConvert;
import com.yc.orika.util.pojo.OrikaVo;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Yanchen
 * @ClassName OrijaUtil
 * @Date 2019/3/18 17:59
 */
public class OrikaUtil {

    /**
     * Description: 实体之间转换 只需要配置一对实体
     *      使用场景: 类似 BeanUtils.copy(A,B);
     *      功能实现: TODO
     *      影响及风险: TODO
     * @param   orikaVo
     * @param  convertList 类型转换适配器
     * @param   sourceObj  源数据 必须含有数据里面
     * @param  targetObj  目标的数据
     * @return
     * @throws
     * @author 闫晨(chen.yan@ucarinc.com)
     * @since 2019/3/20 14:42
     */
    public static Object orikaSignalOnePoConfigure(OrikaVo orikaVo,Object sourceObj, Object targetObj, List<Class<? extends BidirectionalConverter>> convertList) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        //配置适配器
        convert(convertList,mapperFactory);
        //进行bean转换
        onePoCommMethod(orikaVo,mapperFactory);

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(sourceObj, targetObj.getClass());

    }

    /**
     * Description: 实体之间转换 需要配置多个一对实体
     *      使用场景: 类似 BeanUtils.copy(A,B);
     *      功能实现:
     *      影响及风险:
     * @param   list 实体配置
     * @param   sourceObj  源数据 必须含有数据里面
     * @param  targetObj  目标的数据
     *@param  convertList 类型转换适配器
     * @return
     * @throws
     * @author 闫晨(chen.yan@ucarinc.com)
     * @since 2019/3/20 14:41
     */
    public static Object orikaSignalMorePoConfigure(List<OrikaVo> list, Object sourceObj, Object targetObj, List<Class<? extends BidirectionalConverter>> convertList) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        convert(convertList,mapperFactory);

        morePoCommMethod(list, mapperFactory);

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(sourceObj, targetObj.getClass());


    }
    /**
     * Description: List<实体>之间转换 需要配置一对实体
     *      使用场景: 类似 List<A>转换 List<B>
     *      功能实现:
     *      影响及风险:
     * @param   orikaVo 实体配置
     * @param   sourceObj  源数据 必须含有数据里面
     * @param  targetObj  目标的数据
     * @param  convertList 类型转换适配器
     * @return
     * @throws
     * @author 闫晨(chen.yan@ucarinc.com)
     * @since 2019/3/20 14:41
     */
    public static Object orikaListOnePoConfigure(OrikaVo orikaVo, List<?> sourceObj, Object targetObj, List<Class<? extends BidirectionalConverter>> convertList) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        convert(convertList,mapperFactory);

        onePoCommMethod(orikaVo,mapperFactory);

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.mapAsList(sourceObj, targetObj.getClass());

    }


    /**
     * Description: List<实体>之间转换 需要配置多个一对实体
     *       使用场景: 类似 List<A>转换 List<B>
     *      功能实现:
     *      影响及风险:
     * @param   list 实体配置
     * @param   sourceObj  源数据 必须含有数据里面
     * @param  targetObj  目标的数据
     * @param  convertList 类型转换适配器
     * @return
     * @throws
     * @author 闫晨(chen.yan@ucarinc.com)
     * @since 2019/3/20 14:41
     */
    public static Object orikaListMorePoConfigure(List<OrikaVo> list, List<?> sourceObj, Object targetObj, List<Class<? extends BidirectionalConverter>> convertList) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        convert(convertList,mapperFactory);

        morePoCommMethod(list, mapperFactory);

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.mapAsList(sourceObj, targetObj.getClass());

    }

    private static void morePoCommMethod(List<OrikaVo> list, MapperFactory mapperFactory) {

        for (OrikaVo orikaVo : list) {
            ClassMapBuilder<?, ?> classMapBuilder = mapperFactory.classMap(orikaVo.getSource(), orikaVo.getTarget());

            for (Map.Entry<String, String> entry : orikaVo.getMap().entrySet()) {
                classMapBuilder.field(entry.getKey(), entry.getValue());
            }
            if (orikaVo.isFlag()) {
                //是否解析余下的字段
                classMapBuilder.byDefault();
            }
            classMapBuilder.register();
        }
    }


    private static void onePoCommMethod(OrikaVo orikaVo, MapperFactory mapperFactory) {


        ClassMapBuilder<?, ?> classMapBuilder = mapperFactory.classMap(orikaVo.getSource(), orikaVo.getTarget());

        if (orikaVo.getMap() != null) {
            for (Map.Entry<String, String> entry : orikaVo.getMap().entrySet()) {
                classMapBuilder.field(entry.getKey(), entry.getValue());
            }
        }

        if (orikaVo.isFlag()) {
            classMapBuilder.byDefault();//是否解析余下的字段
        }
        classMapBuilder.register();
    }

    private static void  convert(List<Class<? extends BidirectionalConverter>> convertList, MapperFactory mapperFactory){
        //设置全局适配器
        if(convertList!=null){
            ConverterFactory converterFactory = mapperFactory.getConverterFactory();
            for (Class clazz : convertList) {
                try {
                    converterFactory.registerConverter((Converter<Object, Object>) clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
