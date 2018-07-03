package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Square;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

class AviTest {

	@Test
    void streetTest(){
        Scene scene = new Scene("Test");
        Camera camera = new Camera(new Point3D(-270, -280, 710), new Vector(0, -1, 0), new Vector(0, 0, -1));
        camera.rotateAroundY(-20);
       // camera.setFocalLength(200);
       // camera.setApertureSize(2);
//        Camera camera = new Camera(new Point3D(-170, -250, -250), new Vector(0, -1, 0), new Vector(0, 0, -1));
//        camera.rotateY(-90);
       // Camera camera = new Camera(new Point3D(0, -500, -250), new Vector(0, 0, -1), new Vector(0, 1, 0));

        scene.setCamera(camera);
        scene.setScreenDistance(550);
        //scene.setBackgroundColor(new Color(135, 206, 235));
        scene.setBackground(new Color(0, 0, 0));

        scene.setAmbientLight(new AmbientLight(new Color(50,50,50),0.1));

        List<LightSource> lightSources = new ArrayList<>();
        Geometries geometries = new Geometries();

        //ground
        //Plane earth = new Plane(new Point3D(-1,0,0),new Point3D(0,0,-1),new Point3D(1,0,0),new Color(63,63,63), new Material(1,1, 0, 0, 2));
        //geometries.add(earth);
        Color wallsColor1 = new Color(143, 168, 162);
        Color wallsColor2 = new Color(169, 178, 71);
        Color wallsColor3 = new Color(163, 153, 196);

        Color middleWallsColor1 = new Color(102, 0, 51);
        Color middleWallsColor2 = new Color(36, 102, 68);

        buildBuilding (new Point3D(50,-2,0),
                2,100,40,122,new Vector(1,-1,-1),geometries,
                new Boolean[]{true,false}, lightSources,middleWallsColor1,wallsColor1);
        buildBuilding (new Point3D(50,-2,-137),
                6,100,40,100,new Vector(1,-1,-1),geometries,
                new Boolean[]{false,true,false,true,false,true}, lightSources,middleWallsColor2,wallsColor2);
        buildBuilding (new Point3D(50,-2,-255),
                2,100,40,100,new Vector(1,-1,-1),geometries,
                new Boolean[]{false,true}, lightSources,middleWallsColor1,wallsColor3);

        buildBuilding (new Point3D(-50,-2,0),
                2,100,40,122,new Vector(-1,-1,-1),geometries,
                new Boolean[]{false,false}, lightSources,middleWallsColor1,wallsColor3);
        buildBuilding (new Point3D(-50,-2,-137),
                3,100,40,100,new Vector(-1,-1,-1),geometries,
                new Boolean[]{false,false,false}, lightSources,middleWallsColor1,wallsColor2);
        buildBuilding (new Point3D(-50,-2,-255),
                4,100,40,100,new Vector(-1,-1,-1),geometries,
                new Boolean[]{false,true,false,false}, lightSources,middleWallsColor2,wallsColor2);

        buildRoad(new Point3D(0,0,0),100,355,15,15,4,
               geometries,true, lightSources,false);
        buildRoad(new Point3D(50,0,400),100,355,15,15,4,
                geometries,true, lightSources,true);
        buildCross(new Point3D(-50,0,0),100,16,2,5,geometries);
        buildCross(new Point3D(-50,0,-455),100,16,2,5,geometries);
        buildRoad(new Point3D(0,0,455),100,355,15,15,4,
                geometries,true, lightSources,false);


        buildRoad(new Point3D(50,0,-50),100,355,15,15,4,
                geometries,true, lightSources,true);
        Square side1 = new Square(new Point3D(-150,-2,0),new Point3D(-375,-2,0),new Point3D(-150,-2,-100),
                new Color(96, 128, 56),new Material(1,1,2,0,0));
        geometries.add(side1);
        buildTree(new Point3D(-190,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(-220,-2,-70), 20, 50,geometries);

        buildTree(new Point3D(-250,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(-280,-2,-70), 20, 50,geometries);

        buildTree(new Point3D(-310,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(-340,-2,-70), 20, 50,geometries);

        Square side2 = new Square(new Point3D(150,-2,0),new Point3D(375,-2,0),new Point3D(150,-2,-100),
                new Color(96, 128, 56),new Material(1,1,2,0,0));
        geometries.add(side2);
        buildTree(new Point3D(190,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(220,-2,-70), 20, 50,geometries);

        buildTree(new Point3D(250,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(280,-2,-70), 20, 50,geometries);

        buildTree(new Point3D(310,-2,-25), 20, 50,geometries);
        buildTree(new Point3D(340,-2,-70), 20, 50,geometries);

        DirectionalLight directionalLight = new DirectionalLight(new Color(180,190,120).scale(0.2),new Vector(1,2,1));
        //lightSources.add(directionalLight);

        scene.setGeometries(geometries);
        scene.addLight(lightSources);

        ImageWriter imageWriter = new ImageWriter("streetBigDOFLight", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);
        render.setGrid(50, 50, 50);
        render.printImage();
//        render.setMultiThread(true);
//        try {
//            render.renderImage();
//            render.writeToImage();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    private void buildRoad(Point3D middlePoint, double width, double length, int redLineNumber,int middleLineNumber,
                           int ligthNumber,
                           Geometries geometries, Boolean lights, List<LightSource> lightSources,Boolean latitude){
        double sideWalkWidth = width / 6;
        double sideWalkHigh = 2;
        double startZ = middlePoint.getZ().get_coord();
        double startX = middlePoint.getX().get_coord() ;
        double startY = middlePoint.getY().get_coord();



        Color bulbColor = new Color(255,240,179);
        Material bulbMaterial =new Material(0,0,1,0.5,0.9);
        Color bulbStandColor = new Color(143, 168, 162);
        Material bulbStandMaterial = new Material(0.2,0.5,100,0,0);

        double bulbRadius = 3;
        double bulbHigh = 14;
        double lineLengthWithSpace = length/middleLineNumber;
        double lineLength = lineLengthWithSpace * (2/3d);
        double lineWidth = 2;
        double sideWalkRedWitheLength = length/redLineNumber;
        double distance = length / ligthNumber;
        double bulbStandWidth = sideWalkWidth/8;
        double nstartZ = startZ + distance/2;

        if(latitude){
            Square road = new Square(
                    new Point3D(startZ, startY-0.1, startX - width/2 + sideWalkWidth),
                    new Point3D(startZ,startY-0.1,startX + width/2 - sideWalkWidth),
                    new Point3D(startZ - length,startY-0.1,startX - width/2 + sideWalkWidth),
                    new Color(117,117,117),new Material(0.2,0.2,20,0,0));
            geometries.add(road);

            for (int i = 0; i < middleLineNumber; i++){
                Square line = new Square(
                        new Point3D( startZ- (i * lineLengthWithSpace) ,startY - 0.2,startX - lineWidth/2),
                        new Point3D(startZ - (i * lineLengthWithSpace),startY - 0.2,startX + lineWidth/2),
                        new Point3D(startZ - lineLength - (i * lineLengthWithSpace),startY - 0.2,startX - lineWidth/2 ),
                        new Color(240,240,240),new Material(1,1,20,0.01,0));
                geometries.add(line);
            }


            //sidewalk
            Square sideWalkRight = new Square(
                    new Point3D(startZ,startY-sideWalkHigh,startX + sideWalkWidth-(width/2)),
                    new Point3D(startZ,startY-sideWalkHigh, startX - (width/2)),
                    new Point3D(startZ-length,startY-sideWalkHigh, startX + sideWalkWidth-(width/2)),
                    new Color(82, 42, 32),new Material(1,1,20,0.01,0));
            Square sideWalkHeightRight = new Square(
                    new Point3D(startZ,startY, startX + sideWalkWidth-(width/2)),
                    new Point3D(startZ,startY-sideWalkHigh, startX - (width/2)),
                    new Point3D(startZ-length,startY, startX + sideWalkWidth-(width/2)),
                    new Color(36, 16, 14),new Material(1,1,20,0.01,0));

            for (int i = 0; i < redLineNumber; i++){
                Square redLine = new Square(
                        new Point3D(startZ - (i * sideWalkRedWitheLength) ,startY,startX - (width/2)+sideWalkWidth+0.1),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY, startX - (width/2)+sideWalkWidth+0.1),
                        new Point3D(startZ - (i * sideWalkRedWitheLength) ,startY-sideWalkHigh,startX - (width/2)+sideWalkWidth+0.1),
                        new Color(175,10,38),new Material(1,1,20,0,0));

                Square witheLine = new Square(
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY, startX-(width/2)+sideWalkWidth+0.1),
                        new Point3D(startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength),startY, startX-(width/2)+sideWalkWidth+0.1),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY-sideWalkHigh, startX-(width/2)+sideWalkWidth+0.1),
                        new Color(240,240,240),new Material(1,1,20,0.01,0));
                geometries.add(redLine);
                geometries.add(witheLine);
            }

            for (int i = 0; i < redLineNumber; i++){
                Square redLine = new Square(
                        new Point3D(startZ - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001, startX-(width/2)+sideWalkWidth-sideWalkHigh),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength) ,startY - sideWalkHigh + 0.001,startX-(width/2)+sideWalkWidth-sideWalkHigh),
                        new Point3D(startZ - (i * sideWalkRedWitheLength) , startY - sideWalkHigh + 0.001,startX-(width/2)+sideWalkWidth+0.1),
                        new Color(175,10,38),new Material(1,1,20,0,0));

                Square witheLine = new Square(
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength) ,startY - sideWalkHigh + 0.001,startX-(width/2)+sideWalkWidth-sideWalkHigh),
                        new Point3D(startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001, startX-(width/2)+sideWalkWidth-sideWalkHigh),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001, startX-(width/2)+sideWalkWidth+0.1),
                        new Color(240,240,240),new Material(1,1,20,0.01,0));
                geometries.add(redLine,witheLine); 
            }

            Square sideWalkLeft = new Square(
                    new Point3D(startZ,startY-sideWalkHigh, startX +  (width/2) - sideWalkWidth),
                    new Point3D(startZ,startY-sideWalkHigh, startX + (width/2)),
                    new Point3D(startZ-length,startY-sideWalkHigh, startX +  (width/2) - sideWalkWidth),
                    new Color(82, 42, 32),new Material(1,1,20,0.01,0));
            Square sideWalkHeightLeft = new Square(
                    new Point3D(startZ,startY, startX +  (width/2) - sideWalkWidth),
                    new Point3D(startZ,startY-sideWalkHigh, startX + (width/2)),
                    new Point3D(startZ-length,startY, startX +  (width/2) - sideWalkWidth),
                    new Color(36, 16, 14),new Material(1,1,20,0.01,0));


            for (int i = 0; i < redLineNumber; i++){
                Square redLine = new Square(
                        new Point3D(startZ - (i * sideWalkRedWitheLength),startY, startX + (width/2) - sideWalkWidth - 0.1),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY, startX + (width/2)- sideWalkWidth- 0.1),
                        new Point3D(startZ - (i * sideWalkRedWitheLength),startY-sideWalkHigh, startX + (width/2)- sideWalkWidth- 0.1),
                        new Color(175,10,38),new Material(1,1,20,0,0));

                Square witheLine = new Square(
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY, startX+(width/2)- sideWalkWidth - 0.1),
                        new Point3D(startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength) ,startY,startX+(width/2) - sideWalkWidth- 0.1),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY-2, startX+(width/2) - sideWalkWidth- 0.1),
                        new Color(240,240,240),new Material(1,1,20,0.01,0));
                geometries.add(redLine);
                geometries.add(witheLine);
            }

            for (int i = 0; i < redLineNumber; i++){
                Square redLine = new Square(
                        new Point3D(startZ - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001, startX+(width/2) - sideWalkWidth+ sideWalkHigh),
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001,startX+(width/2) - sideWalkWidth+ sideWalkHigh),
                        new Point3D(startZ - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001,startX+(width/2)- sideWalkWidth - 0.1),
                        new Color(175,10,38),new Material(1,1,20,0,0));

                Square witheLine = new Square(
                        new Point3D(startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001,startX+(width/2)- sideWalkWidth + sideWalkHigh),
                        new Point3D(startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength) ,startY - sideWalkHigh + 0.001,startX+(width/2) - sideWalkWidth+ sideWalkHigh),
                        new Point3D( startZ - sideWalkRedWitheLength/2 - (i * sideWalkRedWitheLength),startY - sideWalkHigh + 0.001, startX+(width/2)- sideWalkWidth-0.1),
                        new Color(240,240,240),new Material(1,1,20,0.01,0));
                geometries.add(redLine);
                geometries.add(witheLine);
            }

            geometries.add(sideWalkLeft,sideWalkHeightLeft,sideWalkRight,sideWalkHeightRight);
            

            for (int i = 1; i <= ligthNumber; i++){
                Sphere bulb = new Sphere(bulbRadius,
                        new Point3D( nstartZ - (i * distance),startY-bulbHigh , startX - (width/2) + sideWalkWidth/3),
                        bulbColor,bulbMaterial);
                Square bulb1StandRight = new Square(
                        new Point3D(nstartZ - (i * distance) ,startY,startX -(width/2) + sideWalkWidth/3 - bulbStandWidth),
                        new Point3D(nstartZ - (i * distance),startY, startX -(width/2) + sideWalkWidth/3 + bulbStandWidth),
                        new Point3D(nstartZ - (i * distance),startY-bulbHigh,startX -(width/2) + sideWalkWidth/3 - bulbStandWidth) ,
                        bulbStandColor,bulbStandMaterial);

                SpotLight pLight = new SpotLight(bulbColor,
                        new Point3D(nstartZ - (i * distance),startY - bulbHigh, startX -(width/2) + sideWalkWidth/3),0.01,0.01,new Vector(0,1,0));
                geometries.add(bulb);
                geometries.add(bulb1StandRight);
                if(lights) {
                    lightSources.add(pLight);
                }
            }

            for (int i = 1; i <= ligthNumber; i++){
                Sphere bulb = new Sphere(bulbRadius,
                        new Point3D( nstartZ - (i * distance),startY-bulbHigh , startX + (width/2) - sideWalkWidth/3),
                        bulbColor,bulbMaterial);
                Square bulb1StandRight = new Square(
                        new Point3D(nstartZ - (i * distance),startY, startX +(width/2) - sideWalkWidth/3 - bulbStandWidth),
                        new Point3D(nstartZ - (i * distance) ,startY,startX +(width/2) - sideWalkWidth/3 + bulbStandWidth),
                        new Point3D(nstartZ - (i * distance),startY-bulbHigh, startX +(width/2) - sideWalkWidth/3 - bulbStandWidth),
                        bulbStandColor,bulbStandMaterial);

                SpotLight pLight = new SpotLight(bulbColor,
                        new Point3D(nstartZ - (i * distance) ,startY - bulbHigh,startX +(width/2) - sideWalkWidth/3),0.01,0.01,new Vector(0,1,0));
                geometries.add(bulb);
                geometries.add(bulb1StandRight);
                if(lights) {
                    lightSources.add(pLight);
                }
            }

        } else {

            Square road = new Square(new Point3D(startX - width / 2 + sideWalkWidth, startY-0.1, startZ),
                    new Point3D(startX + width / 2 - sideWalkWidth, startY-0.1, startZ),
                    new Point3D(startX - width / 2 + sideWalkWidth, startY-0.1, startZ - length),
                    new Color(117, 117, 117), new Material(0.2, 0.2, 20, 0, 0));
            geometries.add(road);

            //lines
            for (int i = 0; i < middleLineNumber; i++) {
                Square line = new Square(
                        new Point3D(startX - lineWidth / 2, startY - 0.2, startZ - (i * lineLengthWithSpace)),
                        new Point3D(startX + lineWidth / 2, startY - 0.2, startZ - (i * lineLengthWithSpace)),
                        new Point3D(startX - lineWidth / 2, startY - 0.2, startZ - lineLength - (i * lineLengthWithSpace)),
                        new Color(240, 240, 240), new Material(1, 1, 20, 0.01, 0));
                geometries.add(line);
            }


            //sidewalk
            Square sideWalkRight = new Square(
                    new Point3D(startX + sideWalkWidth - (width / 2), startY - sideWalkHigh, startZ),
                    new Point3D(startX - (width / 2), startY - sideWalkHigh, startZ),
                    new Point3D(startX + sideWalkWidth - (width / 2), startY - sideWalkHigh, startZ - length),
                    new Color(82, 42, 32), new Material(1, 1, 20, 0.01, 0));
            Square sideWalkHeightRight = new Square(
                    new Point3D(startX + sideWalkWidth - (width / 2), startY, startZ),
                    new Point3D(startX - (width / 2), startY - sideWalkHigh, startZ),
                    new Point3D(startX + sideWalkWidth - (width / 2), startY, startZ - length),
                    new Color(36, 16, 14), new Material(1, 1, 20, 0.01, 0));

            for (int i = 0; i < redLineNumber; i++) {
                Square redLine = new Square(
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY, startZ - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY - sideWalkHigh, startZ - (i * sideWalkRedWitheLength)),
                        new Color(175, 10, 38), new Material(1, 1, 20, 0, 0));

                Square witheLine = new Square(
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY, startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY - sideWalkHigh, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Color(240, 240, 240), new Material(1, 1, 20, 0.01, 0));
                geometries.add(redLine,witheLine);
            }

            for (int i = 0; i < redLineNumber; i++) {
                Square redLine = new Square(
                        new Point3D(startX - (width / 2) + sideWalkWidth - sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth - sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY - sideWalkHigh + 0.001, startZ - (i * sideWalkRedWitheLength)),
                        new Color(175, 10, 38), new Material(1, 1, 20, 0, 0));

                Square witheLine = new Square(
                        new Point3D(startX - (width / 2) + sideWalkWidth - sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth - sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength)),
                        new Point3D(startX - (width / 2) + sideWalkWidth + 0.1, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Color(240, 240, 240), new Material(1, 1, 20, 0.01, 0));
                geometries.add(redLine);
                geometries.add(witheLine);
            }

            Square sideWalkLeft = new Square(
                    new Point3D(startX + (width / 2) - sideWalkWidth, startY - sideWalkHigh, startZ),
                    new Point3D(startX + (width / 2), startY - sideWalkHigh, startZ),
                    new Point3D(startX + (width / 2) - sideWalkWidth, startY - sideWalkHigh, startZ - length),
                    new Color(82, 42, 32), new Material(1, 1, 20, 0.01, 0));
            Square sideWalkHeightLeft = new Square(
                    new Point3D(startX + (width / 2) - sideWalkWidth, startY, startZ),
                    new Point3D(startX + (width / 2), startY - sideWalkHigh, startZ),
                    new Point3D(startX + (width / 2) - sideWalkWidth, startY, startZ - length),
                    new Color(36, 16, 14), new Material(1, 1, 20, 0.01, 0));


            for (int i = 0; i < redLineNumber; i++) {
                Square redLine = new Square(
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY, startZ - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY - sideWalkHigh, startZ - (i * sideWalkRedWitheLength)),
                        new Color(175, 10, 38), new Material(1, 1, 20, 0, 0));

                Square witheLine = new Square(
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY, startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY - 2, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Color(240, 240, 240), new Material(1, 1, 20, 0.01, 0));
                geometries.add(redLine,witheLine);
            }

            for (int i = 0; i < redLineNumber; i++) {
                Square redLine = new Square(
                        new Point3D(startX + (width / 2) - sideWalkWidth + sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth + sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY - sideWalkHigh + 0.001, startZ - (i * sideWalkRedWitheLength)),
                        new Color(175, 10, 38), new Material(1, 1, 20, 0, 0));

                Square witheLine = new Square(
                        new Point3D(startX + (width / 2) - sideWalkWidth + sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth + sideWalkHigh, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength - (i * sideWalkRedWitheLength)),
                        new Point3D(startX + (width / 2) - sideWalkWidth - 0.1, startY - sideWalkHigh + 0.001, startZ - sideWalkRedWitheLength / 2 - (i * sideWalkRedWitheLength)),
                        new Color(240, 240, 240), new Material(1, 1, 20, 0.01, 0));
                geometries.add(redLine,witheLine);
            }

            geometries.add(sideWalkLeft,sideWalkHeightLeft,sideWalkRight,sideWalkHeightRight);

           
            for (int i = 1; i <= ligthNumber; i++) {
                Sphere bulb = new Sphere(bulbRadius,
                        new Point3D(startX - (width / 2) + sideWalkWidth / 3, startY - bulbHigh, nstartZ - (i * distance)),
                        bulbColor, bulbMaterial);
                Square bulb1StandRight = new Square(
                        new Point3D(startX - (width / 2) + sideWalkWidth / 3 - bulbStandWidth, startY, nstartZ - (i * distance)),
                        new Point3D(startX - (width / 2) + sideWalkWidth / 3 + bulbStandWidth, startY, nstartZ - (i * distance)),
                        new Point3D(startX - (width / 2) + sideWalkWidth / 3 - bulbStandWidth, startY - bulbHigh, nstartZ - (i * distance)),
                        bulbStandColor, bulbStandMaterial);

                SpotLight pLight = new SpotLight(bulbColor,
                        new Point3D(startX - (width / 2) + sideWalkWidth / 3, startY - bulbHigh, nstartZ - (i * distance)), 0.01, 0.01, new Vector(0, 1, 0));
                geometries.add(bulb);
                geometries.add(bulb1StandRight);
                if (lights) {
                    lightSources.add(pLight);
                }
            }

            for (int i = 1; i <= ligthNumber; i++) {
                Sphere bulb = new Sphere(bulbRadius,
                        new Point3D(startX + (width / 2) - sideWalkWidth / 3, startY - bulbHigh, nstartZ - (i * distance)),
                        bulbColor, bulbMaterial);
                Square bulb1StandRight = new Square(
                        new Point3D(startX + (width / 2) - sideWalkWidth / 3 - bulbStandWidth, startY, nstartZ - (i * distance)),
                        new Point3D(startX + (width / 2) - sideWalkWidth / 3 + bulbStandWidth, startY, nstartZ - (i * distance)),
                        new Point3D(startX + (width / 2) - sideWalkWidth / 3 - bulbStandWidth, startY - bulbHigh, nstartZ - (i * distance)),
                        bulbStandColor, bulbStandMaterial);

                SpotLight pLight = new SpotLight(bulbColor,
                        new Point3D(startX + (width / 2) - sideWalkWidth / 3, startY - bulbHigh, nstartZ - (i * distance)), 0.01, 0.01, new Vector(0, 1, 0));
                geometries.add(bulb);
                geometries.add(bulb1StandRight);
                if (lights) {
                    lightSources.add(pLight);
                }
            }
        }

    }
    private void buildCross(Point3D startPoint, double width, double sideWalkWidth, double sideWalkHigh,
                            int middleLineNumber, Geometries geometries){

        double startZ = startPoint.getZ().get_coord();
        double startX = startPoint.getX().get_coord() ;
        double startY = startPoint.getY().get_coord();
        double lineWidth = (width-sideWalkWidth*2)/middleLineNumber;

        Square road = new Square(
                new Point3D(startX, startY-0.1, startZ),
                new Point3D(startX,startY-0.1,startZ+width),
                new Point3D(startX+width,startY-0.1,startZ),
                new Color(117,117,117),new Material(0.2,0.2,0,0,20));
        geometries.add(road);

        double nstartX = startX + sideWalkWidth + lineWidth/4;
        for (int i = 0; i < middleLineNumber; i++){
            Square line = new Square(
                    new Point3D( nstartX + (i * lineWidth) ,startY - 0.2,startZ),
                    new Point3D(nstartX + (i * lineWidth),startY - 0.2,startZ +sideWalkWidth),
                    new Point3D(nstartX + lineWidth/2 + (i * lineWidth), startY - 0.2,startZ ),
                    new Color(240,240,240),new Material(1,1,20,0.01,0));
            geometries.add(line);
        }

        for (int i = 0; i < middleLineNumber; i++){
            Square line = new Square(
                    new Point3D( nstartX + (i * lineWidth) ,startY - 0.2,startZ + width),
                    new Point3D(nstartX + (i * lineWidth),startY - 0.2,startZ +  width - sideWalkWidth),
                    new Point3D(nstartX + lineWidth/2 + (i * lineWidth), startY - 0.2,startZ + width),
                    new Color(240,240,240),new Material(1,1,20,0.01,0));
            geometries.add(line);
        }
        double nstartZ = startZ + sideWalkWidth + lineWidth/4;
        for (int i = 0; i < middleLineNumber; i++){
            Square line = new Square(
                    new Point3D( startX  ,startY - 0.2,nstartZ + (i * lineWidth)),
                    new Point3D(startX + sideWalkWidth,startY - 0.2,nstartZ +  (i * lineWidth)),
                    new Point3D(startX, startY - 0.2,nstartZ + lineWidth/2 + (i * lineWidth)),
                    new Color(240,240,240),new Material(1,1,20,0.01,0));
            geometries.add(line);
        }
        for (int i = 0; i < middleLineNumber; i++){
            Square line = new Square(
                    new Point3D( startX +width ,startY - 0.2,nstartZ + (i * lineWidth)),
                    new Point3D(startX +width -sideWalkWidth,startY - 0.2,nstartZ +  (i * lineWidth)),
                    new Point3D(startX+width, startY - 0.2,nstartZ + lineWidth/2 + (i * lineWidth)),
                    new Color(240,240,240),new Material(1,1,20,0.01,0));
            geometries.add(line);
        }
        //sidewalk
        Square sideWalkRightBottom = new Square(
                new Point3D(startX,startY-sideWalkHigh,startZ ),
                new Point3D(startX,startY-sideWalkHigh, startZ + sideWalkWidth),
                new Point3D(startX + sideWalkWidth,startY-sideWalkHigh, startZ),
                new Color(82, 42, 32),new Material(1,1,20,0.01,0));
        Square sideWalkHeightRightBottom1 = new Square(
                new Point3D(startX + sideWalkWidth, startY, startZ),
                new Point3D(startX + sideWalkWidth,startY-sideWalkHigh, startZ),
                new Point3D(startX + sideWalkWidth,startY, startZ+ sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));
        Square sideWalkHeightRightBottom2 = new Square(
                new Point3D(startX , startY, startZ+ sideWalkWidth),
                new Point3D(startX ,startY-sideWalkHigh, startZ+ sideWalkWidth),
                new Point3D(startX + sideWalkWidth,startY, startZ+ sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));

        Square sideWalkLeftBottom = new Square(
                new Point3D(startX + width,startY-sideWalkHigh,startZ ),
                new Point3D(startX + width,startY-sideWalkHigh, startZ + sideWalkWidth),
                new Point3D(startX + width - sideWalkWidth,startY-sideWalkHigh, startZ),
                new Color(82, 42, 32),new Material(1,1,20,0.01,0));
        Square sideWalkHeightLeftBottom1 = new Square(
                new Point3D(startX + width - sideWalkWidth, startY, startZ),
                new Point3D(startX + width - sideWalkWidth,startY-sideWalkHigh, startZ),
                new Point3D(startX + width - sideWalkWidth,startY, startZ+ sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));
        Square sideWalkHeightLeftBottom2 = new Square(
                new Point3D(startX + width , startY, startZ+ sideWalkWidth),
                new Point3D(startX + width,startY-sideWalkHigh, startZ+ sideWalkWidth),
                new Point3D(startX + width - sideWalkWidth,startY, startZ+ sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));

        Square sideWalkLeftUp = new Square(
                new Point3D(startX + width,startY-sideWalkHigh,startZ +width),
                new Point3D(startX + width,startY-sideWalkHigh, startZ +width- sideWalkWidth),
                new Point3D(startX + width - sideWalkWidth,startY-sideWalkHigh, startZ+width),
                new Color(82, 42, 32),new Material(1,1,20,0.01,0));
        Square sideWalkHeightLeftUp1 = new Square(
                new Point3D(startX + width - sideWalkWidth, startY, startZ+width),
                new Point3D(startX + width - sideWalkWidth,startY-sideWalkHigh, startZ+width),
                new Point3D(startX + width - sideWalkWidth,startY, startZ+width- sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));
        Square sideWalkHeightLeftUp2 = new Square(
                new Point3D(startX + width, startY, startZ +width- sideWalkWidth),
                new Point3D(startX + width,startY-sideWalkHigh, startZ +width- sideWalkWidth),
                new Point3D(startX + width - sideWalkWidth,startY, startZ+width- sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));

        Square sideWalkRightUp = new Square(
                new Point3D(startX,startY-sideWalkHigh,startZ +width),
                new Point3D(startX,startY-sideWalkHigh, startZ +width- sideWalkWidth),
                new Point3D(startX + sideWalkWidth,startY-sideWalkHigh, startZ+width),
                new Color(82, 42, 32),new Material(1,1,20,0.01,0));
        Square sideWalkHeightRightUp1 = new Square(
                new Point3D(startX + sideWalkWidth, startY, startZ+width),
                new Point3D(startX + sideWalkWidth,startY-sideWalkHigh, startZ+width),
                new Point3D(startX + sideWalkWidth,startY, startZ+width- sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));
        Square sideWalkHeightRightUp2 = new Square(
                new Point3D(startX , startY, startZ + width- sideWalkWidth),
                new Point3D(startX ,startY-sideWalkHigh, startZ+width - sideWalkWidth),
                new Point3D(startX + sideWalkWidth,startY, startZ+width- sideWalkWidth),
                new Color(36, 16, 14),new Material(1,1,20,0.01,0));
        geometries.add(sideWalkRightBottom);
        geometries.add(sideWalkHeightRightBottom1);
        geometries.add(sideWalkHeightRightBottom2);
        geometries.add(sideWalkLeftBottom);
        geometries.add(sideWalkHeightLeftBottom1);
        geometries.add(sideWalkHeightLeftBottom2);
        geometries.add(sideWalkLeftUp);
        geometries.add(sideWalkHeightLeftUp1);
        geometries.add(sideWalkHeightLeftUp2);
        geometries.add(sideWalkRightUp);
        geometries.add(sideWalkHeightRightUp1);
        geometries.add(sideWalkHeightRightUp2);


    }


    //direction is vector with values of 1,-1
    private void buildSimpleHouse(Point3D startPoint,double width, double high, double length, Vector direction,
                                  Geometries geometries, Boolean lights, List<LightSource> lightSources){

        Material wallsMaterial = new Material(0.2,0.5,0,0,100);
        Color wallsColor = new Color(143, 168, 162);
        Color windowColor= new Color(0,0,0);
        Material windowMaterial = new Material(2,3,0,0.5,100);

        double startZ = startPoint.getZ().get_coord();
        double startX = startPoint.getX().get_coord();
        double startY = startPoint.getY().get_coord();

        double directionX = direction.getDirection().getX().get_coord();
        double directionY = direction.getDirection().getY().get_coord();
        double directionZ = direction.getDirection().getZ().get_coord();
        double windowHigh = high/4;
        double windowWidth = width /3;
        double doorHigh = high /3;
        double doorWidth = length /5;
        Square backWallHouse = new Square(new Point3D(startX + directionX * width,startY,startZ),
                new Point3D(startX + directionX * width , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * high, startZ),
                wallsColor,wallsMaterial);

        Square side1BottomWallHouse = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX + directionX *  width,startY,startZ),
                new Point3D(startX, startY + directionY * ((high - windowHigh) /2),startZ),
                wallsColor,wallsMaterial);

        Square side1Middle1WallHouse = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX,startY + directionY * ((high + windowHigh) /2),startZ),
                new Point3D(startX + directionX * ((width-windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ),
                wallsColor,wallsMaterial);

        Square side1Middle2WallHouse = new Square(
                new Point3D(startX + directionX *  ((width+windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX + directionX * ((width+windowWidth)/2),startY + directionY * ((high + windowHigh) /2),startZ),
                new Point3D(startX + directionX *  width,startY + directionY * ((high - windowHigh) /2),startZ),
                wallsColor,wallsMaterial);

        Square side1TopWallHouse = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX + directionX *  width,startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX,startY + directionY * high,startZ),
                wallsColor,wallsMaterial);

        Square side1WindowHouse = new Square(
                new Point3D(startX + directionX * ((width - windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX + directionX * ((width + windowWidth)/2), startY + directionY * ((high - windowHigh) /2),startZ),
                new Point3D(startX + directionX * ((width - windowWidth)/2),startY + directionY * ((high + windowHigh) /2),startZ),
                windowColor,windowMaterial);

        Square side2BottomWallHouse = new Square(
                new Point3D(startX ,startY,startZ + directionZ * length),
                new Point3D(startX + directionX * width,startY,startZ + directionZ *  length),
                new Point3D(startX, startY + directionY * ((high - windowHigh) /2),startZ +directionZ * length),
                wallsColor,wallsMaterial);

        Square side2Middle1WallHouse = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX,startY + directionY * ((high + windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX * ((width-windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                wallsColor,wallsMaterial);

        Square side2Middle2WallHouse = new Square(
                new Point3D(startX + directionX *  ((width + windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX * ((width + windowWidth)/2),startY + directionY * ((high + windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX *  width,startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                wallsColor,wallsMaterial);

        Square side2TopWallHouse = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX *  width,startY + directionY *  ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX,startY + directionY *  high,startZ + directionZ * length),
                wallsColor,wallsMaterial);

        Square side2WindowHouse = new Square(
                new Point3D(startX + directionX * ((width-windowWidth)/2),startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX * ((width+windowWidth)/2), startY + directionY * ((high - windowHigh) /2),startZ + directionZ * length),
                new Point3D(startX + directionX * ((width-windowWidth)/2),startY + directionY * ((high + windowHigh) /2),startZ + directionZ * length),
                windowColor,windowMaterial);

        Square frontWallLeftHouse = new Square(
                new Point3D(startX, startY ,startZ),
                new Point3D(startX, startY,startZ + directionZ * ((length - doorWidth) /2)),
                new Point3D(startX,startY+directionY * high,startZ),
                wallsColor,wallsMaterial);

        Square frontWallRightHouse = new Square(
                new Point3D(startX,startY,startZ + directionZ *  length),
                new Point3D(startX,startY,startZ + directionZ * ((length + doorWidth)/2)),
                new Point3D(startX,startY + directionY * high,startZ + directionZ *  length),
                wallsColor,wallsMaterial);

        Square frontWallAboveDoorHouse = new Square(
                new Point3D(startX,startY+directionY * doorHigh,startZ + directionZ * ((length - doorWidth)/2)),
                new Point3D(startX,startY+directionY * doorHigh,startZ + directionZ * ((length + doorWidth)/2)),
                new Point3D(startX,startY+directionY * high,startZ + directionZ * ((length - doorWidth)/2)),
                wallsColor,wallsMaterial);

        Square frontDoorHouse = new Square(
                new Point3D(startX,startY,startZ + directionZ * ((length - doorWidth)/2)),
                new Point3D(startX,startY,startZ + directionZ * ((length + doorWidth)/2)),
                new Point3D(startX,startY+ directionY * doorHigh,startZ + directionZ * ((length - doorWidth)/2)),
                windowColor,windowMaterial);

        Square roofHouse = new Square(
                new Point3D(startX,startY + directionY * high,startZ),
                new Point3D(startX,startY + directionY * high, startZ + directionZ * length),
                new Point3D(startX + directionX *  width,startY + directionY * high, startZ),
                wallsColor,wallsMaterial);

        Square floorHouse = new Square(
                new Point3D(startX,startY,startZ),
                new Point3D(startX,startY, startZ + directionZ * length),
                new Point3D(startX + directionX *  width,startY,startZ),
                wallsColor,wallsMaterial);

        geometries.add(backWallHouse);

        geometries.add(side1BottomWallHouse);
        geometries.add(side1Middle1WallHouse);
        geometries.add(side1Middle2WallHouse);
        geometries.add(side1TopWallHouse);
        geometries.add(side1WindowHouse);

        geometries.add(side2BottomWallHouse);
        geometries.add(side2Middle1WallHouse);
        geometries.add(side2Middle2WallHouse);
        geometries.add(side2TopWallHouse);
        geometries.add(side2WindowHouse);

        geometries.add(frontWallLeftHouse);
        geometries.add(frontWallRightHouse);
        geometries.add(frontWallAboveDoorHouse);
        geometries.add(frontDoorHouse);

        geometries.add(roofHouse);
        geometries.add(floorHouse);

        PointLight houseLightLeft = new PointLight(
                new Color(255,240,240),
                new Point3D(startX + directionX * (width/2),startY + directionY * (high - 3),startZ + directionZ * (length/2)),0.001,0.001);
        if (lights) {
            lightSources.add(houseLightLeft);
        }

    }

    private void buildBuilding(Point3D startPoint, int numFloors, double width, double floorHigh, double length, Vector direction,
                               Geometries geometries, Boolean[] light, List<LightSource> lightSources,
                               Color decoColor,Color wallsColor){

        buildGroundFloor(startPoint,
                width, floorHigh,10, length, direction, geometries, light[0], lightSources, decoColor, wallsColor);

        startPoint = new Point3D(startPoint.getX().get_coord(),startPoint.getY().get_coord() + direction.getDirection().getY().get_coord() * (floorHigh + 10),startPoint.getZ().get_coord() );

        for(int i=0;i<numFloors-1;++i) {
            buildFloor(new Point3D(startPoint.getX().get_coord(),startPoint.getY().get_coord() +(i* direction.getDirection().getY().get_coord() * floorHigh),startPoint.getZ().get_coord()),
                    width, floorHigh, length, direction, geometries, light[i], lightSources, decoColor, wallsColor);

        }
        buildTopOfBuilding(new Point3D(startPoint.getX().get_coord(),startPoint.getY().get_coord() +((numFloors-1)* direction.getDirection().getY().get_coord() * floorHigh),startPoint.getZ().get_coord()),
                width, 20, length, direction, geometries, lightSources, decoColor, wallsColor);
    }
    //direction is vector with values of 1,-1
    private void buildFloor(Point3D startPoint,double width, double high, double length, Vector direction,
                            Geometries geometries, Boolean light, List<LightSource> lightSources,
                            Color decoColor,Color wallsColor){

        Material wallsMaterial = new Material(1,1,0,0,2);
        Color windowColor= new Color(5,5,5);
        Material windowMaterial = new Material(2,3,0,0.9,2);

        double startZ = startPoint.getZ().get_coord();
        double startX = startPoint.getX().get_coord();
        double startY = startPoint.getY().get_coord();

        double directionX = direction.getDirection().getX().get_coord();
        double directionY = direction.getDirection().getY().get_coord();
        double directionZ = direction.getDirection().getZ().get_coord();

        double frontPart = length/3;
        double windowHigh = high * 3/4;
        double windowWidth = frontPart * 3/4;


        Square backWall = new Square(new Point3D(startX + directionX * width,startY,startZ),
                new Point3D(startX + directionX * width , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * high, startZ),
                wallsColor,wallsMaterial);

        Square side1Wall = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX + directionX *  width ,startY,startZ),
                new Point3D(startX, startY + directionY * high ,startZ),
                wallsColor,wallsMaterial);

        Square side2Wall = new Square(
                new Point3D(startX ,startY,startZ + directionZ * length),
                new Point3D(startX + directionX * width,startY,startZ + directionZ *  length),
                new Point3D(startX, startY + directionY * high ,startZ + directionZ * length),
                wallsColor,wallsMaterial);


        Square frontWallLeftBottom = new Square(
                new Point3D(startX, startY ,startZ),
                new Point3D(startX, startY,startZ + directionZ * frontPart),
                new Point3D(startX,startY + directionY * ((high-windowHigh)/2), startZ),
                wallsColor,wallsMaterial);
        Square frontWallLeftLeft = new Square(
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),startZ),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),startZ ),
                wallsColor,wallsMaterial);

        Square frontWallLeftRight = new Square(
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),startZ + directionZ * frontPart),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart+windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),startZ + directionZ *  frontPart),
                wallsColor,wallsMaterial);

        Square frontWallLeftTop = new Square(
                new Point3D(startX,startY + directionY * high,startZ),
                new Point3D(startX,startY + directionY * high,startZ + directionZ * frontPart),
                new Point3D(startX,startY + directionY * ((high+windowHigh)/2),startZ ),
                wallsColor,wallsMaterial);

        Square frontWallLeftWindow = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),startZ + directionZ * ((frontPart+windowWidth)/2)),
                windowColor,windowMaterial);

        double middleStartZ = startZ + directionZ * frontPart;
        double middleStartX = startX + -1 * directionX * (length/20);
        double midWindowHigh = windowHigh * 3/5;
        double midWindowWidth = windowWidth * 5/7;

        Square frontWallMiddleSide1 = new Square(
                new Point3D(startX, startY ,middleStartZ),
                new Point3D(startX, startY + directionY * high,middleStartZ),
                new Point3D(middleStartX,startY, middleStartZ),
                decoColor,wallsMaterial);
        Square frontWallMiddleSide2 = new Square(
                new Point3D(startX, startY ,middleStartZ + directionZ *  frontPart ),
                new Point3D(startX, startY + directionY * high,middleStartZ + directionZ *  frontPart),
                new Point3D(middleStartX,startY, middleStartZ + directionZ *  frontPart),
                decoColor,wallsMaterial);

        Square frontWallMiddleBottom = new Square(
                new Point3D(middleStartX, startY ,middleStartZ),
                new Point3D(middleStartX, startY,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY+directionY * ((high-midWindowHigh)/2), middleStartZ),
                decoColor,wallsMaterial);

        Square frontWallMiddleLeft = new Square(
                new Point3D(middleStartX,startY + directionY * ((high + midWindowHigh)/2),middleStartZ),
                new Point3D(middleStartX,startY + directionY * ((high + midWindowHigh)/2),middleStartZ + directionZ * ((frontPart-midWindowWidth)/2)),
                new Point3D(middleStartX,startY + directionY * ((high - midWindowHigh)/2),middleStartZ ),
                decoColor,wallsMaterial);

        Square frontWallMiddleRight = new Square(
                new Point3D(middleStartX,startY + directionY * ((high + midWindowHigh)/2),middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY + directionY * ((high + midWindowHigh)/2),middleStartZ + directionZ * ((frontPart+midWindowWidth)/2)),
                new Point3D(middleStartX,startY + directionY * ((high - midWindowHigh)/2),middleStartZ + directionZ *  frontPart),
                decoColor,wallsMaterial);

        Square frontWallMiddleTop = new Square(
                new Point3D(middleStartX,startY + directionY * high,middleStartZ),
                new Point3D(middleStartX,startY + directionY * high,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY + directionY * ((high+midWindowHigh)/2),middleStartZ ),
                decoColor,wallsMaterial);

        Square frontWallMiddleWindow = new Square(
                new Point3D(middleStartX,startY + directionY * ((high - midWindowHigh)/2),middleStartZ + directionZ * ((frontPart-midWindowWidth)/2)),
                new Point3D(middleStartX,startY + directionY * ((high + midWindowHigh)/2),middleStartZ + directionZ * ((frontPart-midWindowWidth)/2)),
                new Point3D(middleStartX,startY + directionY * ((high - midWindowHigh)/2),middleStartZ + directionZ * ((frontPart+midWindowWidth)/2)),
                windowColor,windowMaterial);


        double lastStartZ = startZ + directionZ * frontPart * 2;
        Square frontWallRightBottom = new Square(
                new Point3D(startX, startY ,lastStartZ),
                new Point3D(startX, startY,lastStartZ + directionZ * frontPart),
                new Point3D(startX,startY+directionY * ((high-windowHigh)/2), lastStartZ),
                wallsColor,wallsMaterial);
        Square frontWallRightLeft = new Square(
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),lastStartZ),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),lastStartZ ),
                wallsColor,wallsMaterial);

        Square frontWallRightRight = new Square(
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * frontPart),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart+windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ *  frontPart),
                wallsColor,wallsMaterial);

        Square frontWallRightTop = new Square(
                new Point3D(startX,startY + directionY * high,lastStartZ),
                new Point3D(startX,startY + directionY * high,lastStartZ + directionZ * frontPart),
                new Point3D(startX,startY + directionY * ((high+windowHigh)/2),lastStartZ ),
                wallsColor,wallsMaterial);

        Square frontWallRightWindow = new Square(
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,startY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ * ((frontPart+windowWidth)/2)),
                windowColor,windowMaterial);


        Square floor = new Square(
                new Point3D(startX,startY,startZ),
                new Point3D(startX,startY, startZ + directionZ * length),
                new Point3D(startX + directionX *  width,startY,startZ),
                wallsColor,wallsMaterial);

        geometries.add(backWall);
        geometries.add(side1Wall);
        geometries.add(side2Wall);
        geometries.add(frontWallLeftBottom);
        geometries.add(frontWallLeftLeft);
        geometries.add(frontWallLeftRight);
        geometries.add(frontWallLeftTop);
        geometries.add(frontWallLeftWindow);

        geometries.add(frontWallMiddleBottom);
        geometries.add(frontWallMiddleLeft);
        geometries.add(frontWallMiddleRight);
        geometries.add(frontWallMiddleTop);
        geometries.add(frontWallMiddleWindow);
        geometries.add(frontWallMiddleSide1);
        geometries.add(frontWallMiddleSide2);

        geometries.add(frontWallRightBottom);
        geometries.add(frontWallRightLeft);
        geometries.add(frontWallRightRight);
        geometries.add(frontWallRightTop);
        geometries.add(frontWallRightWindow);

        geometries.add(floor);

        PointLight p_light = new PointLight(
                new Color(255,240,240),
                new Point3D(startX + directionX * (width/2),startY + directionY * (high + 2),startZ + directionZ * (length/2)),0.001,0.001);
        if (light) {
            lightSources.add(p_light);
        }

    }

    private void buildGroundFloor(Point3D startPoint,double width, double high,double decorateHigh, double length, Vector direction,
                                  Geometries geometries, Boolean light, List<LightSource> lightSources
                                    , Color decoColor,Color wallsColor){

        Material wallsMaterial = new Material(1,1,2,0,0);
        Color windowColor= new Color(5,5,5);
        Material windowMaterial = new Material(2,3,2,0,0.9);

        double startZ = startPoint.getZ().get_coord();
        double startX = startPoint.getX().get_coord();
        double startY = startPoint.getY().get_coord();

        double directionX = direction.getDirection().getX().get_coord();
        double directionY = direction.getDirection().getY().get_coord();
        double directionZ = direction.getDirection().getZ().get_coord();

        double frontPart = length/3;
        double windowHigh = high * 3/4;
        double windowWidth = frontPart * 3/4;
        high += decorateHigh;
        Square backWallBottom = new Square(
                new Point3D(startX + directionX * width,startY,startZ),
                new Point3D(startX + directionX * width , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * decorateHigh, startZ),
                decoColor,wallsMaterial);

        Square side1WallBottom = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX + directionX *  width ,startY,startZ),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ),
                decoColor,wallsMaterial);

        Square side2WallBottom = new Square(
                new Point3D(startX ,startY,startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY,startZ + directionZ *  length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ * length),
                decoColor,wallsMaterial);

        Square frontWallLeftBottomDeco = new Square(
                new Point3D(startX , startY,startZ),
                new Point3D(startX, startY,startZ + directionZ *  frontPart),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ ),
                decoColor,wallsMaterial);

        Square frontWallRightBottomDeco = new Square(
                new Point3D(startX , startY,startZ + directionZ *  frontPart * 2),
                new Point3D(startX, startY,startZ + directionZ * length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ *  frontPart * 2),
                decoColor,wallsMaterial);

        double groundStartY = startY + directionY * decorateHigh;
        Square backWall = new Square(new Point3D(startX + directionX * width,groundStartY,startZ),
                new Point3D(startX + directionX * width , groundStartY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,groundStartY + directionY * high, startZ),
                wallsColor,wallsMaterial);

        Square side1Wall = new Square(
                new Point3D(startX ,groundStartY,startZ),
                new Point3D(startX + directionX *  width ,groundStartY,startZ),
                new Point3D(startX, groundStartY + directionY * high ,startZ),
                wallsColor,wallsMaterial);

        Square side2Wall = new Square(
                new Point3D(startX ,groundStartY,startZ + directionZ * length),
                new Point3D(startX + directionX * width,groundStartY,startZ + directionZ *  length),
                new Point3D(startX, groundStartY + directionY * high ,startZ + directionZ * length),
                wallsColor,wallsMaterial);


        Square frontWallLeftBottom = new Square(
                new Point3D(startX, groundStartY ,startZ),
                new Point3D(startX, groundStartY,startZ + directionZ * frontPart),
                new Point3D(startX,groundStartY + directionY * ((high-windowHigh)/2), startZ),
                wallsColor,wallsMaterial);
        Square frontWallLeftLeft = new Square(
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),startZ),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),startZ ),
                wallsColor,wallsMaterial);

        Square frontWallLeftRight = new Square(
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),startZ + directionZ * frontPart),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart+windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),startZ + directionZ *  frontPart),
                wallsColor,wallsMaterial);

        Square frontWallLeftTop = new Square(
                new Point3D(startX,groundStartY + directionY * high,startZ),
                new Point3D(startX,groundStartY + directionY * high,startZ + directionZ * frontPart),
                new Point3D(startX,groundStartY + directionY * ((high+windowHigh)/2),startZ ),
                wallsColor,wallsMaterial);

        Square frontWallLeftWindow = new Square(
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),startZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),startZ + directionZ * ((frontPart+windowWidth)/2)),
                windowColor,windowMaterial);

        double middleStartZ = startZ + directionZ * frontPart;
        double middleStartX = startX + -1 * directionX * (length/20);
        //here it is the door
        double midWindowHigh = frontPart * 19/20;
        double midWindowWidth = frontPart * 7/10;

        Square frontWallMiddleSide1 = new Square(
                new Point3D(startX, startY ,middleStartZ),
                new Point3D(startX, groundStartY + directionY * high,middleStartZ),
                new Point3D(middleStartX,startY, middleStartZ),
                decoColor,wallsMaterial);
        Square frontWallMiddleSide2 = new Square(
                new Point3D(startX, startY ,middleStartZ + directionZ *  frontPart ),
                new Point3D(startX, groundStartY + directionY * high,middleStartZ + directionZ *  frontPart),
                new Point3D(middleStartX,startY, middleStartZ + directionZ *  frontPart),
                decoColor,wallsMaterial);

        Square frontWallMiddleLeft = new Square(
                new Point3D(middleStartX,startY ,middleStartZ),
                new Point3D(middleStartX,startY + directionY  * midWindowHigh,middleStartZ ),
                new Point3D(middleStartX,startY ,middleStartZ + directionZ * ((frontPart-midWindowWidth)/2) ),
                decoColor,wallsMaterial);

        Square frontWallMiddleRight = new Square(
                new Point3D(middleStartX,startY ,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY + directionY * midWindowHigh,middleStartZ + directionZ *  frontPart),
                new Point3D(middleStartX,startY ,middleStartZ + directionZ * ((frontPart+midWindowWidth)/2)),
                decoColor,wallsMaterial);

        Square frontWallMiddleTop = new Square(
                new Point3D(middleStartX,startY + directionY * high , middleStartZ),
                new Point3D(middleStartX,startY + directionY * high,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY + directionY * midWindowHigh,middleStartZ ),
                decoColor,wallsMaterial);

        Square frontWallMiddleWindow = new Square(
                new Point3D(middleStartX,startY ,middleStartZ + directionZ * ((frontPart-midWindowWidth)/2)),
                new Point3D(middleStartX,startY + directionY * midWindowHigh,middleStartZ + directionZ * ((frontPart-midWindowWidth)/2)),
                new Point3D(middleStartX,startY ,middleStartZ + directionZ * ((frontPart+midWindowWidth)/2)),
                windowColor,windowMaterial);


        double lastStartZ = startZ + directionZ * frontPart * 2;
        Square frontWallRightBottom = new Square(
                new Point3D(startX, groundStartY ,lastStartZ),
                new Point3D(startX, groundStartY,lastStartZ + directionZ * frontPart),
                new Point3D(startX,groundStartY+directionY * ((high-windowHigh)/2), lastStartZ),
                wallsColor,wallsMaterial);
        Square frontWallRightLeft = new Square(
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),lastStartZ),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),lastStartZ ),
                wallsColor,wallsMaterial);

        Square frontWallRightRight = new Square(
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * frontPart),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart+windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ *  frontPart),
                wallsColor,wallsMaterial);

        Square frontWallRightTop = new Square(
                new Point3D(startX,groundStartY + directionY * high,lastStartZ),
                new Point3D(startX,groundStartY + directionY * high,lastStartZ + directionZ * frontPart),
                new Point3D(startX,groundStartY + directionY * ((high+windowHigh)/2),lastStartZ ),
                wallsColor,wallsMaterial);

        Square frontWallRightWindow = new Square(
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high + windowHigh)/2),lastStartZ + directionZ * ((frontPart-windowWidth)/2)),
                new Point3D(startX,groundStartY + directionY * ((high - windowHigh)/2),lastStartZ + directionZ * ((frontPart+windowWidth)/2)),
                windowColor,windowMaterial);


        Square floor = new Square(
                new Point3D(startX,groundStartY,startZ),
                new Point3D(startX,groundStartY, startZ + directionZ * length),
                new Point3D(startX + directionX *  width,groundStartY,startZ),
                wallsColor,wallsMaterial);

        geometries.add(backWallBottom);
        geometries.add(side1WallBottom);
        geometries.add(side2WallBottom);
        geometries.add(frontWallLeftBottomDeco);
        geometries.add(frontWallRightBottomDeco);

        geometries.add(backWall);
        geometries.add(side1Wall);
        geometries.add(side2Wall);

        geometries.add(frontWallLeftBottom);
        geometries.add(frontWallLeftLeft);
        geometries.add(frontWallLeftRight);
        geometries.add(frontWallLeftTop);
        geometries.add(frontWallLeftWindow);

        geometries.add(frontWallMiddleLeft);
        geometries.add(frontWallMiddleRight);
        geometries.add(frontWallMiddleTop);
        geometries.add(frontWallMiddleWindow);
        geometries.add(frontWallMiddleSide1);
        geometries.add(frontWallMiddleSide2);

        geometries.add(frontWallRightBottom);
        geometries.add(frontWallRightLeft);
        geometries.add(frontWallRightRight);
        geometries.add(frontWallRightTop);
        geometries.add(frontWallRightWindow);

        geometries.add(floor);

        PointLight p_light = new PointLight(
                new Color(255,240,240),
                new Point3D(startX + directionX * (width/2),groundStartY + directionY * (high + 2),startZ + directionZ * (length/2)),0.001,0.001);
        if (light) {
            lightSources.add(p_light);
        }

    }

    private void buildTopOfBuilding(Point3D startPoint,double width, double high, double length, Vector direction,
                                    Geometries geometries, List<LightSource> lightSources,
                                    Color decoColor,Color wallsColor){

        Material wallsMaterial = new Material(1,1,2,0,0);


        double startZ = startPoint.getZ().get_coord();
        double startX = startPoint.getX().get_coord();
        double startY = startPoint.getY().get_coord();

        double directionX = direction.getDirection().getX().get_coord();
        double directionY = direction.getDirection().getY().get_coord();
        double directionZ = direction.getDirection().getZ().get_coord();

        double frontPart = length/3;
        double decorateHigh = high/2;

        Square bottomTopDeco = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX  , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY, startZ),
                wallsColor,wallsMaterial);

        Square backWallTopDeco = new Square(
                new Point3D(startX + directionX * width,startY,startZ),
                new Point3D(startX + directionX * width , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * decorateHigh, startZ),
                decoColor,wallsMaterial);

        Square side1WallTopDeco = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX + directionX *  width ,startY,startZ),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ),
                decoColor,wallsMaterial);

        Square side2WallTopDeco = new Square(
                new Point3D(startX ,startY,startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY,startZ + directionZ *  length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ * length),
                decoColor,wallsMaterial);

        Square frontWallLeftTopDeco = new Square(
                new Point3D(startX , startY,startZ),
                new Point3D(startX, startY,startZ + directionZ *  frontPart),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ ),
                decoColor,wallsMaterial);

        Square frontWallRightTopDeco = new Square(
                new Point3D(startX , startY,startZ + directionZ *  frontPart * 2),
                new Point3D(startX, startY,startZ + directionZ * length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ *  frontPart * 2),
                decoColor,wallsMaterial);


        double middleStartZ = startZ + directionZ * frontPart;
        double middleStartX = startX + -1 * directionX * (length/20);

        Square frontWallMiddleSide1 = new Square(
                new Point3D(startX, startY ,middleStartZ),
                new Point3D(startX, startY + directionY * decorateHigh ,middleStartZ),
                new Point3D(middleStartX,startY, middleStartZ),
                decoColor,wallsMaterial);

        Square frontWallMiddleSide2 = new Square(
                new Point3D(startX, startY ,middleStartZ + directionZ *  frontPart ),
                new Point3D(startX, startY + directionY * decorateHigh,middleStartZ + directionZ *  frontPart),
                new Point3D(middleStartX,startY, middleStartZ + directionZ *  frontPart),
                decoColor,wallsMaterial);

        Square frontWallMiddle = new Square(
                new Point3D(middleStartX,startY + directionY * decorateHigh , middleStartZ),
                new Point3D(middleStartX,startY + directionY * decorateHigh,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY ,middleStartZ),
                decoColor,wallsMaterial);
        geometries.add(bottomTopDeco);
        geometries.add(backWallTopDeco);
        geometries.add(side1WallTopDeco);
        geometries.add(side2WallTopDeco);
        geometries.add(frontWallLeftTopDeco);
        geometries.add(frontWallRightTopDeco);
        geometries.add(frontWallMiddle);

        geometries.add(frontWallMiddleSide1);
        geometries.add(frontWallMiddleSide2);

        double zizOffset =decorateHigh/2;
        startY = startY + directionY * decorateHigh;
        startX = startX + directionX * (-zizOffset);
        startZ = startZ + directionZ * (-zizOffset);
        width = width + zizOffset * 2;
        length = length + zizOffset * 2;
        frontPart = length/3;
        Square backWallTopDecoZiz = new Square(
                new Point3D(startX + directionX * width,startY,startZ),
                new Point3D(startX + directionX * width , startY, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * decorateHigh, startZ),
                wallsColor,wallsMaterial);

        Square side1WallTopDecoZiz = new Square(
                new Point3D(startX ,startY,startZ),
                new Point3D(startX + directionX *  width ,startY,startZ),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ),
                wallsColor,wallsMaterial);

        Square side2WallTopDecoZiz = new Square(
                new Point3D(startX ,startY,startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY,startZ + directionZ *  length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ * length),
                wallsColor,wallsMaterial);

        Square frontWallLeftTopDecoZiz = new Square(
                new Point3D(startX , startY,startZ),
                new Point3D(startX, startY,startZ + directionZ *  frontPart),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ ),
                wallsColor,wallsMaterial);

        Square frontWallRightTopDecoZiz = new Square(
                new Point3D(startX , startY,startZ + directionZ *  frontPart * 2),
                new Point3D(startX, startY,startZ + directionZ * length),
                new Point3D(startX, startY + directionY * decorateHigh ,startZ + directionZ *  frontPart * 2),
                wallsColor,wallsMaterial);


        middleStartZ = startZ + directionZ * frontPart;
        middleStartX = startX + -1 * directionX * (length/20);

        Square frontWallMiddleSide1Ziz = new Square(
                new Point3D(startX, startY ,middleStartZ),
                new Point3D(startX, startY + directionY * decorateHigh ,middleStartZ),
                new Point3D(middleStartX,startY, middleStartZ),
                wallsColor,wallsMaterial);

        Square frontWallMiddleSide2Ziz = new Square(
                new Point3D(startX, startY ,middleStartZ + directionZ *  frontPart ),
                new Point3D(startX, startY + directionY * decorateHigh,middleStartZ + directionZ *  frontPart),
                new Point3D(middleStartX,startY, middleStartZ + directionZ *  frontPart),
                wallsColor,wallsMaterial);

        Square frontWallMiddleZiz = new Square(
                new Point3D(middleStartX,startY + directionY * decorateHigh , middleStartZ),
                new Point3D(middleStartX,startY + directionY * decorateHigh,middleStartZ + directionZ * frontPart),
                new Point3D(middleStartX,startY ,middleStartZ),
                wallsColor,wallsMaterial);

        geometries.add(backWallTopDecoZiz);
        geometries.add(side1WallTopDecoZiz);
        geometries.add(side2WallTopDecoZiz);
        geometries.add(frontWallLeftTopDecoZiz);
        geometries.add(frontWallRightTopDecoZiz);
        geometries.add(frontWallMiddleZiz);

        geometries.add(frontWallMiddleSide1Ziz);
        geometries.add(frontWallMiddleSide2Ziz);

        Square topWallTopDeco = new Square(
                new Point3D(startX ,startY + directionY * decorateHigh,startZ),
                new Point3D(startX  , startY + directionY * decorateHigh, startZ + directionZ * length),
                new Point3D(startX + directionX * width ,startY + directionY * decorateHigh, startZ),
                wallsColor,wallsMaterial);
        Square topWallTopDecoZiz = new Square(
                new Point3D(middleStartX ,startY + directionY * decorateHigh,middleStartZ),
                new Point3D(middleStartX  , startY + directionY * decorateHigh, middleStartZ + directionZ *  frontPart),
                new Point3D(startX ,startY + directionY * decorateHigh, middleStartZ),
                wallsColor,wallsMaterial);
        geometries.add(topWallTopDeco);
        geometries.add(topWallTopDecoZiz);


    }
    
    private void buildTree(Point3D startPoint,double width,double high,Geometries geometries){

        double startX = startPoint.getX().get_coord();
        double startY = startPoint.getY().get_coord();
        double startZ = startPoint.getZ().get_coord();

        Square base = new Square(
                new Point3D(startX + ((width-width/4)/2),startY,startZ+width/2),
                new Point3D(startX + ((width-width/4)/2),startY-high/6,startZ+width/2),
                new Point3D(startX + ((width+width/4)/2),startY,startZ+width/2),new Color(83, 49, 24),new Material(1,1,0,0,2));
        geometries.add(base);
        Color leafC = new Color(11, 20, 0);
        Material leafM = new Material(1,1,2,0,0);
        double nwidth = width/2;
        double moveZ = 0;
        double accMidHigh = 4 * high / 6;
        for(int i = 1; i<5; ++i) {
            Point3D middlePoint = new Point3D(startX + width / 2, startY - accMidHigh, startZ + width / 2);
            accMidHigh = accMidHigh + (high/(6*i*i*i));

            Vector v1 = new Vector(-2, 0, 1).normalization().scaleMult(nwidth);
            Vector v2 = new Vector(0, 0, 1).scaleMult(nwidth );
            Vector v3 = new Vector(2, 0, 1).normalization().scaleMult(nwidth);
            Vector v4 = v1.scaleMult(-1);
            Vector v5 = v2.scaleMult(-1);
            Vector v6 = v3.scaleMult(-1);

            Point3D sPoint = new Point3D(startX +  width / 2 , startY - (high *(i)) /6, startZ + moveZ);
            Point3D nextPoint = sPoint.addVector(v1);
            Triangle t1 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);
            moveZ =+ (nwidth-(nwidth * 0.85));

            nwidth = nwidth * 0.85 ;
            sPoint = nextPoint;
            nextPoint = nextPoint.addVector(v2);
            Triangle t2 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);

            sPoint = nextPoint;
            nextPoint = nextPoint.addVector(v3);
            Triangle t3 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);

            sPoint = nextPoint;
            nextPoint = nextPoint.addVector(v4);
            Triangle t4 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);

            sPoint = nextPoint;
            nextPoint = nextPoint.addVector(v5);
            Triangle t5 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);

            sPoint = nextPoint;
            nextPoint = nextPoint.addVector(v6);
            Triangle t6 = new Triangle(new Point3D(sPoint),
                    new Point3D(middlePoint),
                    new Point3D(nextPoint), leafC, leafM);

            geometries.add(t1, t2, t3, t4, t5 ,t6);
        }


    }


  
}
