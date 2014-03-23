package com.oneskyapp.eclipse.sync.api.model;

import com.google.gson.annotations.SerializedName;

public class ImportDetail{
		private Long id;
		
//		FIXME unable to parse		
//		@SerializedName("created_at")
//		private Date createdAt;
		
		@SerializedName("created_at_timestamp")
		private Long createdAtTimestamp;

		public Long getId() {
			return id;
		}

//		public Date getCreatedAt() {
//			return createdAt;
//		}

		public Long getCreatedAtTimestamp() {
			return createdAtTimestamp;
		}
		
		
	}