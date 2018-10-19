# Common Libs for Pivaiot Projects

## Purpose

所有项目通用的部分

## What's inside

* http
* data
* database
* util

## How to use



### Gradle 配置

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
	dependencies {
	        implementation 'com.github.pivaiot:common:1.0.1'
	}

```

### 其它构建工具配置

* [详细说明 jitpack](https://jitpack.io/#pivaiot/common)
