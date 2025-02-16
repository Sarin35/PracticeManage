package com.practice.practicemanage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * 日志工具类，提供统一的日志记录方式。
 * 支持不同日志级别的记录，并支持通过 MDC 添加上下文信息。
 * MDC（Mapped Diagnostic Context）是 SLF4J 提供的一种在多线程环墿下记录日志的方法。
 * getLogger 方法的作用是根据传入的类（clazz）返回一个与该类关联的 Logger 实例。
 * 这个 Logger 实例是通过 LoggerFactory.getLogger(clazz) 创建的，其中 LoggerFactory 是 SLF4J 提供的工厂类，负责创建和管理 Logger 实例。
 */
@Component
public class LogUtil {

    /**
     * 获取日志记录器
     * @param clazz 日志记录的类
     * @return Logger 实例
     */
    private Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * 记录信息日志
     * @param clazz 日志记录的类
     * @param message 日志内容
     */
    public void info(Class<?> clazz, String message) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).info(message);
        } finally {
            MDC.clear(); // 确保清除 MDC 中的信息，避免对其他线程的影响
        }
    }

    /**
     * 记录信息日志（带格式化参数）
     * @param clazz 日志记录的类
     * @param message 日志内容
     * @param args 日志格式化参数
     */
    public void info(Class<?> clazz, String message, Object... args) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).info(message, args);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录调试日志
     * @param clazz 日志记录的类
     * @param message 日志内容
     */
    public void debug(Class<?> clazz, String message) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).debug(message);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录调试日志（带格式化参数）
     * @param clazz 日志记录的类
     * @param message 日志内容
     * @param args 日志格式化参数
     */
    public void debug(Class<?> clazz, String message, Object... args) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).debug(message, args);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录警告日志
     * @param clazz 日志记录的类
     * @param message 日志内容
     */
    public void warn(Class<?> clazz, String message) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).warn(message);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录警告日志（带格式化参数）
     * @param clazz 日志记录的类
     * @param message 日志内容
     * @param args 日志格式化参数
     */
    public void warn(Class<?> clazz, String message, Object... args) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).warn(message, args);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录错误日志
     * @param clazz 日志记录的类
     * @param message 日志内容
     */
    public void error(Class<?> clazz, String message) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).error(message);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录错误日志（带格式化参数）
     * @param clazz 日志记录的类
     * @param message 日志内容
     * @param args 日志格式化参数
     */
    public void error(Class<?> clazz, String message, Object... args) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).error(message, args);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 记录错误日志，带异常信息
     * @param clazz 日志记录的类
     * @param message 日志内容
     * @param throwable 异常信息
     */
    public void error(Class<?> clazz, String message, Throwable throwable) {
        try {
            MDC.put("className", clazz.getSimpleName()); // 添加类名到 MDC
            getLogger(clazz).error(message, throwable);
        } finally {
            MDC.clear();
        }
    }
}
