package com.ankoki.blossom.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Shapes {
    private static final double _2PI = 6.283185307179586;
    private static final double _3PI = Math.PI + Math.PI + Math.PI;

    /**
     * (W.I.P) A method to get all the points to make up a giant donut looking thing.
     *
     * @param center The center of the torus.
     * @param majorRadius The radius of the outside.
     * @param minorRadius The radius of the inside.
     * @param density The density of the particles.
     * @return All locations to make up the torus.
     */
    public static List<Location> getTorus(Location center, double majorRadius, double minorRadius, double density) {
        List<Location> torusPoints = new ArrayList<>();
        double pointsOnMajor = _2PI * majorRadius * density;
        double pointsOnMinor = _2PI * minorRadius * density;
        double deltaMajor = _3PI / pointsOnMajor;
        double deltaMinor = _3PI / pointsOnMinor;
        double thetaMajor = 0;
        for (int i = 0; i < pointsOnMajor; i++) {
            Vector tubeOffset = new Vector(Math.cos(thetaMajor * majorRadius), 0, Math.sin(thetaMajor * majorRadius));
            Location tube = center.clone();
            tube.add(tubeOffset);
            double rotationAngle = 540 - thetaMajor;
            double thetaMinor = 0;
            for (int ii = 0; ii < pointsOnMinor; ii++) {
                Vector offset = new Vector(Math.cos(thetaMinor * minorRadius), Math.sin(thetaMinor * minorRadius), 0);
                offset.rotateAroundY(rotationAngle);
                Location toOffset = tube.clone();
                toOffset.add(offset);
                torusPoints.add(toOffset);
                thetaMinor += deltaMinor;
            }
            thetaMajor += deltaMajor;
        }
        return torusPoints;
    }

    /**
     * A method to get the list of locations in a circle.
     *
     * @param center The centre of the circle.
     * @param radius The radius of the circle.
     * @param totalPoints The total amount of points to make up the circle.
     * @return The list of locations to make up a circle.
     */
    public static List<Location> getCircle(Location center, double radius, double totalPoints) {
        World world = center.getWorld();
        double increment = _2PI/totalPoints;
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < totalPoints; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    /**
     * A method to get a list of locations of the points of a star.
     *
     * @param center The centre of the star.
     * @param radius The radius of the circle which makes up the outer edge of the star.
     * @param vertices The amount of vertices a star has.
     * @return A list of locations of all points of a star.
     */
    public static List<Location> getStarPoints(Location center, double radius, int vertices) {
        List<Location> locations = new ArrayList<>();
        double delta = _2PI / vertices;
        boolean bug = false;
        for (double theta = 0; theta < _2PI; theta += delta) {
            if (!bug && vertices == 6) {
                bug = true;
                continue;
            }
            Vector offset = new Vector(Math.sin(theta) * radius, 0, Math.cos(theta) * radius);
            Location vertex = center.clone();
            vertex.add(offset);
            if (!locations.contains(vertex)) {
                locations.add(vertex);
            }
        }
        return locations;
    }

    /**
     * A method to get a list of locations to make up a line.
     *
     * @param pointOne The first point of the line.
     * @param pointTwo The second point of the line.
     * @param space The space between each point of a line.
     * @return A list of locations to make up a line.
     */
    public static List<Location> getLine(Location pointOne, Location pointTwo, double space) {
        List<Location> points = new ArrayList<>();
        double distance = pointOne.distance(pointTwo);
        Vector p1 = pointOne.toVector();
        Vector p2 = pointTwo.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        for (double length = 0; length < distance; p1.add(vector)) {
            points.add(p1.toLocation(pointOne.getWorld()));
            length += space;
        }
        return points;
    }

    /**
     * A method to get a list of locations to make up a cone.
     *
     * @param center The centre of the bottom of the cone.
     * @param radius The radius of the face.
     * @param height The height of the cone.
     * @param density The density of the cone.
     * @param pointsPerCircle The amount of points per circle.
     * @return A list of locations to make up a cone.
     */
    public static List<Location> drawCone(Location center, double radius, double height, double density, double pointsPerCircle) {
        List<Location> points = new ArrayList<>();
        double space = 1 / density;
        double amount = height / space;
        double interval = radius / amount;
        Location c = center.clone();
        for (int i = 0; i <= amount; i++) {
            c.add(0, space, 0);
            points.addAll(Shapes.getCircle(c, radius, pointsPerCircle));
            radius = radius - interval;
        }
        return points;
    }
}