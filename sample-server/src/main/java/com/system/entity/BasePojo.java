package com.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7185816713463006928L;

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
	
    protected Integer version;
	
    @Column(updatable = false)
	protected Date createdDtm;

    @Column(updatable = false)
    protected Integer createdBy;

    protected Date lastModifiedDtm;

    protected Integer lastModifiedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Date getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedDtm() {
		return lastModifiedDtm;
	}

	public void setLastModifiedDtm(Date lastModifiedDtm) {
		this.lastModifiedDtm = lastModifiedDtm;
	}

	public Integer getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public void updateCommonInfo() {
		Integer userId = 0; // use id = 0 temporarily
        Date now = new Date();
        if (this.getId() == null || this.getId() == 0) { // New PoJo
            this.setVersion(0);
            if (this.getCreatedDtm() == null) {
                this.setCreatedDtm(now);
            }
            if (this.getCreatedBy() == null || this.getCreatedBy().equals(0)) {
                this.setCreatedBy(userId == null ? 0 : userId);
            }
            this.setLastModifiedDtm(now);
            this.setLastModifiedBy(userId == null ? 0 : userId);
        } else { // Update this
            this.setVersion(this.getVersion() == null ? 1 : this.getVersion() + 1);
            this.setLastModifiedDtm(now);
            this.setLastModifiedBy(userId == null ? 0 : userId);
        }
    }

}
