package com.cooksys.ftd.assessment.filesharing.model.db;

import java.sql.Blob;

public class File {

	private Integer fileID;
	private String filePath;
	private Blob fileData;

	public File() {
		super();
	}

	public File(Integer fileID, String filePath, Blob fileData) {
		super();
		this.fileID = fileID;
		this.filePath = filePath;
		this.fileData = fileData;
	}

	public Integer getFileID() {
		return fileID;
	}

	public void setFileID(Integer fileID) {
		this.fileID = fileID;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Blob getFileData() {
		return fileData;
	}

	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileData == null) ? 0 : fileData.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((fileID == null) ? 0 : fileID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		if (fileData == null) {
			if (other.fileData != null)
				return false;
		} else if (!fileData.equals(other.fileData))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (fileID == null) {
			if (other.fileID != null)
				return false;
		} else if (!fileID.equals(other.fileID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "File [fileID=" + fileID + ", filePath=" + filePath + ", fileData=" + fileData + "]";
	}
}
