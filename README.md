# log-file-analysis
Using KMeans to analyze text entries and classify them for studying changes in logging over time.

KMeans is for unsupervised machine learning.

The idea is to classify log entries. First, learn from a sample of log files. Then apply constantly to monitor logs for anomalities such as a sudden increase in logs of a category. Or entries that are not classifiable, i e new events which differ more than a threshold from every known category.
