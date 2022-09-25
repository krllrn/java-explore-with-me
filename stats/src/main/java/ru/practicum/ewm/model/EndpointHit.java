package ru.practicum.ewm.model;

import com.vladmihalcea.hibernate.type.basic.Inet;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLInetType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@TypeDef(
        name = "ipv4",
        typeClass = PostgreSQLInetType.class,
        defaultForType = Inet.class)
@Table(name = "statistic")
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip", columnDefinition = "inet")
    private String ip;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;
}
