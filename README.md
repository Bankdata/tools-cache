[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dk.bankdata.jaxrs/security/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dk.bankdata.tools/cache/)
[![Javadoc](https://javadoc.io/badge/dk.bankdata.jaxrs/security/badge.svg)](https://www.javadoc.io/doc/dk.bankdata.tools/cache)
[![Build Status](https://travis-ci.com/Bankdata/jaxrs-security.svg?branch=master)](https://travis-ci.com/Bankdata/tools-cache)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Known Vulnerabilities](https://snyk.io/test/github/Bankdata/jaxrs-security/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/Bankdata/tools-cache?targetFile=build.gradle)

# Overview

This project contains some simple utilities related to building JAX-RS bases
REST services. The contents are centered around security, e.g., validation
JWT (JWS) bearer tokens, doing simple encryption of values and more.

## Getting Started

See how to add this library to your project here 
https://search.maven.org/artifact/dk.bankdata.jaxrs/security

### Prerequisites

This library needs java 1.8 to function correctly

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

Eksample:
```
export REDIS_MASTERNAME=redis-master
export REDIS_SENTINELS=master-0:12345,slave-0:12345,slave-1:12345,slave:12345
export REDIS_PROFILE=server //Can be set to either `local` or `server`
```

#### CacheHandler
This is an example of how to use the library :
``` java
@ApplicationScoped
public class ItemCacheHandler {
    @Inject
    CacheHandler cacheHandler;

    public void setItemInCache(String itemKey, Item item) {
        cacheHandler.set(itemKey, item);
    }

    public Item getItemFromCache(String itemKey) {
        return cacheHandler.get(itemKey, Item.class);
    }
}
```