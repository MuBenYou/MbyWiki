package com.mby.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Aspect//使用详解 AOP为Aspect Oriented Programming的缩写
@Component
public class LogAspect {

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /** 定义一个切点 */
    @Pointcut("execution(public * com.mby.controller..*Controller.*(..))")
    public void controllerPointcut() {}

//    @Resource
//    private SnowFlake snowFlake;

    @Before("controllerPointcut()")//@Before：前置通知，即执行业务代码之前，我们要去做的事情。
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 增加日志流水号
//        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));

        // 开始打印请求日志   详解https://blog.csdn.net/weixin_43899069/article/details/120698772
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//requestcontextholder获取request里的参数
        HttpServletRequest request = attributes.getRequest();//获得请求的全部参数 原本是servlet的赋值给http请求 这样可以打印出http的参数
        Signature signature = joinPoint.getSignature();//joinpoint  用来获取代理类和被代理类的信息。 AOP需要用到joinPoint这个连接点 来拿类里的参数
        String name = signature.getName();//你处理的类的名称

        // 打印请求信息
        LOG.info("------------- 开始 -------------");
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址: {}", request.getRemoteAddr());//getRemoteAddr 远程地址

//        RequestContext.setRemoteAddr(getRemoteIp(request));

        // 打印请求参数
        Object[] args = joinPoint.getArgs();//joinpoint  用来获取代理类和被代理类的信息 joinpoint.getargs():获取带参方法的参数 注:就是获取组件中xxx方法中的参数,如果xxx方法中有多个参数,那么这个方法机会返回多个参数.
		// LOG.info("请求参数: {}", JSONObject.toJSONString(args));

		Object[] arguments  = new Object[args.length];//new Object类型 为arguments
        for (int i = 0; i < args.length; i++) {//循环
            if (args[i] instanceof ServletRequest//判断类的类型是否一样
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {//MultipartFile一种可以接收使用多种请求方式来进行上传文件的代表形式
                continue;
            }
            arguments[i] = args[i];//将args赋值给arguments
        }
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();//PropertyPreFilters自定义过滤器
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();//addFilter添加过滤器，MySimplePropertyPreFilter字段过滤功能
        excludefilter.addExcludes(excludeProperties);//过滤规则
        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter));//JSONObject把实体对象转换成Json字符串,排除不要的字段
    }

    @Around("controllerPointcut()")//环绕通知，即围绕业务内容，前面执行一点东西，后面再执行一点东西。
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();//开始时间
        Object result = proceedingJoinPoint.proceed();//环绕通知 ProceedingJoinPoint 执行proceed方法的作用是让目标方法执行，这也是环绕通知和前置、后置通知方法
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();//alibaba.fastjson依赖内的方法 用来自定义过滤器
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();//将对象转换成json格式的时候，常常需要排除一些字段（例如延迟加载的字段）。在fastjson库中，我们可以使用SimplePropertyPreFilter忽略掉这些属性。
        excludefilter.addExcludes(excludeProperties);
        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 使用nginx做反向代理，需要用该方法才能取到真实的远程IP
     * @param request
     * @return
     */
    public String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
