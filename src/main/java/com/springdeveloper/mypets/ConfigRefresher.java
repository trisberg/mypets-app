
package com.springdeveloper.mypets;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ConfigRefresher {

	private static Logger logger = LoggerFactory.getLogger(ConfigRefresher.class);

	private WatchService watchService;

	private WatchKey key = null;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ContextRefresher refresher;

	@PostConstruct
	public void refreshBeans() {
		String configDir = System.getProperty("user.dir");
		if (configDir.endsWith("/")) {
			configDir = configDir + "config";
		} else {
			configDir = configDir + "/config";
		}
		if (!Files.exists(Paths.get(configDir))) {
			configDir = System.getProperty("user.dir");
		}
		logger.info("Initializing config watcher for " + configDir);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(configDir);
			path.register(watchService, 
				StandardWatchEventKinds.ENTRY_MODIFY, 
				StandardWatchEventKinds.ENTRY_DELETE, 
				StandardWatchEventKinds.ENTRY_CREATE);
		} catch (IOException e) {
			throw new IllegalStateException("Error initializing config watcher", e);
		}
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while ((key = watchService.take()) != null) {
						logger.info("Checking changes for @RefreshScope sources.");
						if (key != null) {
							for (WatchEvent<?> event : key.pollEvents()) {
								logger.info("Event '" + event.kind() + "' for '" + event.context() + "'.");
							}
							Set<String> r = refresher.refresh();
							logger.info("Properties refreshed: " + r);
							key.reset();
						}
					}
				} catch (InterruptedException e) {
					logger.info("Watching interrupted: " + e);
				}
			}
		});
	}
}
