package com.mobileme.photolocator.api;

import java.io.Serializable;

public class HSPhoto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idx;

	private String name;
	private long createdate;
	private byte[] image;
	private byte[] imagemini;
	private double latitude;
	private double longitude;
    private int status;
	private long statusdate;
    private String statusMsg;

	
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(long senddate) {
        this.statusdate = senddate;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }


	public byte[] getImagemini() {
		return imagemini;
	}

	public void setImagemini(byte[] imagemini) {
		this.imagemini = imagemini;
	}


	@Override
    public String toString() {
        return "HSPhoto{" +
                "idx=" + idx +
                ", name='" + name + '\'' +
                ", createdate=" + createdate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status=" + status +
                ", statusdate=" + statusdate +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
