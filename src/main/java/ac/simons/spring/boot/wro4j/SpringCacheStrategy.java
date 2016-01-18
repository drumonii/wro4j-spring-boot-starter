/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ac.simons.spring.boot.wro4j;

import ro.isdc.wro.cache.CacheStrategy;

import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

/**
 * A simple strategy based on Spring caches. Delegates the heavy lifting to the
 * cache configured by or through Spring.
 *
 * @param <K> Type of the keys
 * @param <V> Type of the values
 * @author Michael J. Simons, 2016-01-18
 */
public class SpringCacheStrategy<K, V> implements CacheStrategy<K, V> {

	private final CacheManager cacheManager;

	private final String cacheName;

	public SpringCacheStrategy(CacheManager cacheManager, String cacheName) {
		this.cacheManager = cacheManager;
		this.cacheName = cacheName;
	}

	@Override
	public void put(K key, V value) {
		this.cacheManager.getCache(this.cacheName).put(key, value);
	}

	@Override
	public V get(K key) {
		final ValueWrapper valueWrapper = this.cacheManager.getCache(this.cacheName).get(key);
		return (V) (valueWrapper == null ? null : valueWrapper.get());
	}

	@Override
	public void clear() {
		this.cacheManager.getCache(this.cacheName).clear();
	}

	@Override
	public void destroy() {
		this.clear();
	}

}
