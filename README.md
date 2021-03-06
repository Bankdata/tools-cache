[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dk.bankdata.tools/cache/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dk.bankdata.tools/cache/)
[![Javadoc](https://javadoc.io/badge/dk.bankdata.tools/cache/badge.svg)](https://www.javadoc.io/doc/dk.bankdata.tools/cache)
[![Build Status](https://travis-ci.com/Bankdata/tools-cache.svg?branch=master)](https://travis-ci.com/Bankdata/tools-cache)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Known Vulnerabilities](https://snyk.io/test/github/Bankdata/tools-cache/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/Bankdata/tools-cache?targetFile=build.gradle)

# Overview

This project contains some helpful functions when working with a redis sentinel setup for cacheing.

## Getting Started

See how to add this library to your project here 
https://search.maven.org/artifact/dk.bankdata.tools/cache

### Prerequisites

This library needs Java 8 or higher to function correctly

[Download here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Author

* **Kenneth Bøgedal** - [bogedal](https://github.com/bogedal)

## License

This project is licensed under the MIT License

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.


## Usage

In the following section there will be provided code examples of each part of this library

#### Pre-conditions
The following environemnt variables needs to be present for this library to function.
- REDIS_MASTERNAME - The name of your cache
- REDIS_SENTINELS - List of the sentinels in the cluster
- REDIS_PASSWORD - Password for Redis - omit if no password is needed
- REDIS_PROFILE - Decides if redis is to run on a stub or on the server.

Example:
```
export REDIS_MASTERNAME=redis-master
export REDIS_SENTINELS=master-0:12345,slave-0:12345,slave-1:12345,slave:12345
export REDIS_PROFILE=server //Can be set to either `local` or `server`
```

#### CacheHandler
This is a few examples of how to use some of the library :
``` java
@ApplicationScoped
public class ItemCacheHandler {
    @Inject
    private CacheHandler cacheHandler;

    public void setItemInCache(String itemKey, Item item) {
        cacheHandler.set(itemKey, item);
    }

    public Item getItemFromCache(String itemKey) {
        return cacheHandler.get(itemKey, Item.class);
    }
}
```
``` java
@ApplicationScoped
public class ItemCacheHandler {
    @Inject
    private CacheHandler cacheHandler;

    public void setInCache(String key, String payload) {
        cacheHandler.set(key, payload);
    }

    public String getFromCache(String key) {
        return cacheHandler.get(key);
    }
}
```
``` java
@ApplicationScoped
public class ItemCacheHandler {
    @Inject
    private CacheHandler cacheHandler;

    public void setInCache(String key, List<Item> payload) {
        cacheHandler.set(key, payload);
    }

    public List<Item> getFromCache(String key) {
        return cacheHandler.getList(key, Item.class);
    }
}
```
