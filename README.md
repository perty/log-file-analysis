# log-file-analysis
Using K-Means to analyze text entries and classify them for studying changes in logging over time.

K-Means is for unsupervised machine learning.

The idea is to classify log entries. First, learn from a sample of log files. 
Then apply constantly to monitor logs for anomalies such as a sudden increase in logs of a category.
Or entries that are not classifiable, i e new events which differ more than a threshold from every 
known category.

## K-Means
K-Means works by defining the number of categories and then iterate until the features of the samples put them in the 
same category. The categories are found by starting with randomly placed centroids that represents the first assumption 
of what each category has as feature set. The samples are associated with the centroid they are most similar to. Then, 
each centroid is changed (moved in feature space) so that it represents what would be the most typical feature set of 
its category. 

The next iteration will associate some samples with a different centroid due to the centroids move in feature space. 
When there are no changes to associations, the classification is stable and learning is considered done.

The stabilization is not guaranteed, an oscillation of samples may occur so there needs to be a maximum number of 
iterations or a time budget.

The number of categories is heavily dependent on the domain and how it is modeled. In the case of log files, is a log 
entry just a string or should the log level be one feature and the content another? The size could be a feature, too. 

We could be interested in a series of connected log entries, not just isolated entries. That would create categories 
around use cases which would help us monitor for changes in usage.
