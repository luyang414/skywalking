/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.agent.core.remote;

import org.skywalking.apm.agent.core.boot.BootService;
import org.skywalking.apm.agent.core.boot.DefaultNamedThreadFactory;
import org.skywalking.apm.agent.core.conf.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Collector 发现服务
 *
 * The <code>CollectorDiscoveryService</code> is responsible for start {@link DiscoveryRestServiceClient}.
 *
 * @author wusheng
 */
public class CollectorDiscoveryService implements BootService {
    private ScheduledFuture<?> future;

    @Override
    public void beforeBoot() throws Throwable {

    }

    @Override
    public void boot() throws Throwable {
        future = Executors.newSingleThreadScheduledExecutor(new DefaultNamedThreadFactory("CollectorDiscoveryService"))
            .scheduleAtFixedRate(new DiscoveryRestServiceClient(), 0,
                Config.Collector.DISCOVERY_CHECK_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void afterBoot() throws Throwable {

    }

    @Override
    public void shutdown() throws Throwable {
        future.cancel(true);
    }

}
