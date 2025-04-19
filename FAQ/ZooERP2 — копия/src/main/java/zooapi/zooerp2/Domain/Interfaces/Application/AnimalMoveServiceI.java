package zooapi.zooerp2.Domain.Interfaces.Application;

import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Entities.Enclosure;

import java.util.UUID;

public interface AnimalMoveServiceI {
    void move(int animalId, int enclosureId);
}
