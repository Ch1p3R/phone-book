package com.phonebook.repository.local.filemanager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseFileManager<T> {
	private static final Logger LOGGER = Logger.getLogger(BaseFileManager.class.getName());
	public static final String ID_PREFIX = "\"id\":\"";
	public static final String ACCOUNT_NAME_PREFIX = "\"name\":\"";
	public static final String TEMP_FILE_PREFIX = "/temp";

	private String relativePath;
	private String baseDirectory;
	private String fileName;

	private File fileStorage;
	private ObjectMapper mapper;

	public BaseFileManager(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}
	
	public abstract T save(T entity);

	public void provideFileStorage() {
		baseDirectory = (baseDirectory != null && !baseDirectory.isEmpty()) ? baseDirectory
				: System.getProperty("user.dir");
		relativePath = relativePath != null ? relativePath : "";
		fileName = fileName != null ? fileName : "/storage.file";
		File file = new File(baseDirectory + relativePath + fileName);
		if (file.exists()) {
			fileStorage = file;
			LOGGER.info("Using existed file storage with absolute path: " + file.getAbsolutePath());
		} else {
			createStorageDir(file.getParentFile());
			createStorageFile(file);
			fileStorage = file;
		}

	}

	private void createStorageDir(File file) {
		try {
			file.mkdirs();
			LOGGER.info("Created DIR for storage with path: " + file.getPath());
		} catch (SecurityException se) {
			LOGGER.log( Level.SEVERE, se.toString(), se);
		}
	}

	private void createStorageFile(File file) {
		try {
			file.createNewFile();
			System.out.println("Created file for storage with path: " + file.getPath());
		} catch (IOException e) {
			LOGGER.log( Level.SEVERE, e.toString(), e);
		}
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public File getFileStorage() {
		return fileStorage;
	}

	public void setFileStorage(File fileStorage) {
		this.fileStorage = fileStorage;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

}