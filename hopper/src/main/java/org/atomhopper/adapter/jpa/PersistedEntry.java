package org.atomhopper.adapter.jpa;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Entries")
public class PersistedEntry implements Serializable {

    @Id
    @Column(name = "EntryID")
    private String entryId;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Feed")
    private PersistedFeed feed;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(name = "CategoryEntryReferences",
    joinColumns = {
        @JoinColumn(name = "entryId", referencedColumnName = "EntryID")},
    inverseJoinColumns = {
        @JoinColumn(name = "category", referencedColumnName = "Term")})
    private Set<PersistedCategory> categories;

    @Column(name = "EntryBody")
    @Lob
    private byte[] entryBody;

    @Basic(optional = false)
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Basic(optional = false)
    @Column(name = "DateLastUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdated;

    public PersistedEntry() {
        categories = Collections.EMPTY_SET;

        final Calendar localNow = Calendar.getInstance(TimeZone.getDefault());
        localNow.setTimeInMillis(System.currentTimeMillis());

        creationDate = localNow.getTime();
        dateLastUpdated = localNow.getTime();
    }

    public PersistedEntry(String entryId) {
        this();

        categories = new HashSet<PersistedCategory>();
        this.entryId = entryId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public Set<PersistedCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<PersistedCategory> categories) {
        this.categories = categories;
    }

    public byte[] getEntryBody() {
        return this.entryBody;
    }

    public void setEntryBody(byte[] entryBody) {
        this.entryBody = entryBody;
    }

    public PersistedFeed getFeed() {
        return feed;
    }

    public void setFeed(PersistedFeed feed) {
        this.feed = feed;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
}
