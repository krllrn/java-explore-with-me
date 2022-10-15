package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "statistic")
public class EndpointHit implements Comparable<EndpointHit> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

    @Override
    public int compareTo(EndpointHit o) {
        if (app.equals(o.app) && uri.equals(o.uri) && ip.equals(o.ip)) {
            return 0;
        } else {
            return -1;
        }
    }
}
