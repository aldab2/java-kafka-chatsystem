package org.example;


import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.*;

public class chat_client extends javax.swing.JFrame {

    private String message="";

     String bootstrapServers ;

    private String topic = "dev";
    private String username = "Barnawi";

    Properties consumerProperties;
    Properties producerProperties;

    KafkaProducer<String, String> producer;
    private AdminClient client = null;
    ArrayList<String> topics = new ArrayList<>();
    Collection<ConsumerGroupListing> consumerGroups;

    public chat_client(String s) {


        this.bootstrapServers = s;

        Map<String, Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        conf.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000");
        client = AdminClient.create(conf);






        try {
            ListTopicsResult listTopicsResult = client.listTopics();
            Map<String, TopicListing> topics = listTopicsResult.namesToListings().get(15,TimeUnit.SECONDS);
            topics.forEach((topic,topicInfo)-> this.topics.add(topic));

            ListConsumerGroupsResult consumerGroup = client.listConsumerGroups();
            this.consumerGroups = consumerGroup.all().get();

            consumerGroups.removeIf(group -> group.toString().contains("Empty"));




        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        catch (TimeoutException te){
            JOptionPane.showMessageDialog(null,"System Timed out, check connection and try again","Warning",JOptionPane.WARNING_MESSAGE);
            System.exit(-1);
        }

        JOptionPaneMultiInput optionPanel = new JOptionPaneMultiInput(this.topics,this.consumerGroups);

        this.username = optionPanel.username;
        this.topic = optionPanel.chatroom;
        System.out.println("Topic is "+this.topic+" username is "+this.username);


        if(this.username == null){
            JOptionPane.showMessageDialog(null,"error in username, maybe its online already","Warning",JOptionPane.WARNING_MESSAGE);
            System.exit(-1);
        }
        setupProducerAndConsumer();

        producer = new KafkaProducer<>(producerProperties);




        
        initComponents();
        
        this.setTitle("Client");
        this.setVisible(true);
        status.setVisible(true);
    }

    private void setupProducerAndConsumer() {
        // create consumer configs
        this.consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, username);
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProperties.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,"1500");
        consumerProperties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"4000");

        // -----
        this.producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //System.out.println("Connecting to "+ bootstrapServers+ "...");
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        jTextField1.setToolTipText("text\tType your message here...");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 370, 410, 40);

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(420, 370, 80, 40);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 80, 490, 280);
        DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jLabel2.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Client");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(140, 20, 180, 40);

        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setText("...");
        jPanel1.add(status);
        status.setBounds(10, 50, 300, 40);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(508, 441));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

        sendMessage(jTextField1.getText());
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

       sendMessage(jTextField1.getText());
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void startRunning()
    {


        try (KafkaConsumer<String,String> consumer =new KafkaConsumer<>(consumerProperties)) {

            status.setText("Attempting Connection ...");
            System.out.println("Subscribing to topic: "+ topic + "...");
            consumer.subscribe(Arrays.asList(topic));
            Map<String, List<PartitionInfo>> topics = consumer.listTopics();
            System.out.println("Subscription done. Topics are:"+  topics.keySet());
            status.setText("Connected as: " + username + "   ------- Chatroom is : "+ topic);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() { // Function runs every MINUTES minutes.
                    // Run the code you want here
                    int CONNECTION_TEST_TIMEOUT_SECONDS = 15; // or whatever is appropriate for your environment

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Runnable testTask = consumer::listTopics;

                    Future<?> future = executor.submit(testTask);
                    try {
                        System.out.println(("Retesting connection"));
                        ListTopicsResult listTopicsResult = client.listTopics();
                        Map<String, TopicListing> topics = listTopicsResult.namesToListings().get(2,TimeUnit.SECONDS);
                         future.get(CONNECTION_TEST_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                    } catch (TimeoutException te) {
                        consumer.wakeup();
                        JOptionPane.showMessageDialog(null,"Could not communicate with the server within " + CONNECTION_TEST_TIMEOUT_SECONDS + " seconds, Exiting.","Warning",JOptionPane.WARNING_MESSAGE);
                        System.exit(-1);

                    } catch (InterruptedException e) {
                        // Nothing to do. Maybe a warning in the log?
                        JOptionPane.showMessageDialog(null,"Consumer got interrupted","Warning",JOptionPane.WARNING_MESSAGE);

                    } catch (ExecutionException e) {
                        if(!e.getMessage().contains("not safe for multi-threaded"))
                            JOptionPane.showMessageDialog(null,"Consumer got Execution Exception"+ e.getMessage() + e.getCause().getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null,"Consumer got  Exception"+ e.getMessage() + e.getCause().getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);

                    }
                }
            }, 0, 1000 * 30 );


            int i = 0;
            while(!message.equals("Client - END")){
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(1));

                for (ConsumerRecord<String, String> record : records){
                    System.out.println("Key: " + record.key() + ", Value: " + record.value());
                    System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());

                    message = record.value();
                    chatArea.append("\n"+ message);
                }


                int MINUTES = 10; // The delay in minutes


            }

        }
       try
       {

            //status.setText("Connected to: " + connection.getInetAddress().getHostName());


            //output = new ObjectOutputStream(connection.getOutputStream());
            //output.flush();
            //input = new ObjectInputStream(connection.getInputStream());

            whileChatting();
       }
       catch(IOException ioException)
       {
            ioException.printStackTrace();
       }
    }
    
    private void whileChatting() throws IOException
    {



    }
  
    
    private void sendMessage(String message)
    {
        try {

            //chatArea.append("\nMe:"+message);
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(topic, username+":"+ message);

            Future<RecordMetadata> f = producer.send(producerRecord);
            f.get(15, TimeUnit.SECONDS);
            producer.flush();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
        }
//        try
//        {
//
//            //chatArea.append("\nME(Client) - "+message);
//            //String encryptedmsg = encyrDecry.encrypt(message, secretKey);
//            //System.out.println(encryptedmsg);
////            output.writeObject("                                                             (enc):" + encryptedmsg);
////            EncryDecry encyrDecry = new EncryDecry();
////            message = encyrDecry.decrypt(encryptedmsg, secretKey);
////            output.writeObject("                                                             Client(decrypt) - " + message);
////            output.flush();
//
//
//        }
//        catch(IOException ioException)
//        {
//            chatArea.append("\n Unable to Send Message");
//        }
    }
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
