//package com.cstor.http.client;
//
//import org.apache.commons.codec.binary.Base64;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//
//public class AmbariClient
//{
//    private String host;
//
//    private String clusterName;
//
//    private String user;
//
//    private String password;
//
//    private RestTemplate restTemplate;
//
//    public AmbariClient(String host, String clusterName, String user, String password)
//    {
//        this.host = host;
//        this.clusterName = clusterName;
//        this.user = user;
//        this.password = password;
//
//        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//        MediaType plainTextType = new MediaType("text", "plain");
//        MediaType jsonType = new MediaType("application", "json");
//
//        supportedMediaTypes.add(plainTextType);
//        supportedMediaTypes.add(jsonType);
//
//        MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
//        mappingJacksonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//        mappingJacksonHttpMessageConverter.getObjectMapper().configure(
//                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        messageConverters.add(mappingJacksonHttpMessageConverter);
//
//        restTemplate = new RestTemplate();
//        restTemplate.setMessageConverters(messageConverters);
//    }
//
//    public <T> T getAmbariHadoopObject(String url, Class<?> clazz)
//    {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory()
//        {
//            @Override
//            protected void prepareConnection(HttpURLConnection connection, String httpMethod)
//                    throws IOException
//            {
//                super.prepareConnection(connection, httpMethod);
//
//                String authorisation = user + ":" + password;
//                String encodedAuthorisation = Base64.encodeBase64String(authorisation.getBytes());
//                connection.setRequestProperty("Authorization", "Basic " + encodedAuthorisation);
//                connection.setConnectTimeout(30000);
//                connection.setReadTimeout(120000);
//            }
//        };
//
//        restTemplate.setRequestFactory(requestFactory);
//
//        String fullUrl = "http://" + host + "/api/v1/clusters/" + clusterName + url;
//        return (T) restTemplate.getForObject(fullUrl, clazz);
//    }
//
//    /**
//     * Model class to hold the data from the JSON response.
//     */
//    private static final class JobTrackerData
//    {
//        public class Metrics
//        {
//            public class MapReduce
//            {
//                public class JobTracker
//                {
//                    @JsonProperty("jobs_running")
//                    private Integer jobsRunning;
//
//                    @JsonProperty("running_maps")
//                    private Integer runningMaps;
//
//                    @JsonProperty("running_reduces")
//                    private Integer runningReduces;
//                }
//
//                @JsonProperty("jobtracker")
//                private JobTracker jobTracker;
//            }
//
//            @JsonProperty("mapred")
//            private MapReduce mapReduce;
//        }
//
//        @JsonProperty("metrics")
//        private Metrics metrics;
//    }
//
//    public static void main(String[] args)
//    {
//        AmbariClient client = new AmbariClient("ambari.teradata.com",
//                "clustername", "admin", "admin");
//        JobTrackerData data = client.getAmbariHadoopObject(
//                "/services/MAPREDUCE/components/JOBTRACKER", JobTrackerData.class);
//        System.out.println("Jobs running: " + data.metrics.mapReduce.jobTracker.jobsRunning);
//        System.out.println("Map tasks running: " + data.metrics.mapReduce.jobTracker.runningMaps);
//        System.out.println("Reduce tasks running: "
//                + data.metrics.mapReduce.jobTracker.runningReduces);
//    }
//}