package org.scrum.psd.battleship.controller.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShipState {
    private final Ship ship;
    private final Set<Position> hits;

    private ShipState(Ship ship) {
        this.ship = ship;
        this.hits = new HashSet<>();
    }

    public String getName() {
        return ship.getName();
    }

    public static List<ShipState> from(List<Ship> ships) {
        List<ShipState> out = new ArrayList<>(ships.size());
        for (Ship ship : ships) {
            out.add(new ShipState(ship));
        }
        return out;
    }

    public boolean checkHit(Position pos) {
        for (Position position : ship.getPositions()) {
            if (hits.contains(position)) {
                continue;
            }
            if (pos.equals(position)) {
                hits.add(pos);
                return true;
            }
        }
        return false;
    }

    public boolean isSunk() {
        for (Position position : ship.getPositions()) {
            if (!hits.contains(position)) {
                return false;
            }
        }
        return true;
    }
}
