import React from "react";
import { loadModules } from "esri-loader";
import forest from "../img/forest.png";
import Parking from "../img/Parking.png";
import buvettel from "../img/cafe.png";
import restaurantl from "../img/restaurant.png";
import stadel from "../img/terrain.png";
import bibliol from "../img/library.png";
import taxil from "../img/taxi.png";
import busl from "../img/bus.png";
import Wcl from "../img/wc.png";
import { AutoComplete } from "primereact/autocomplete";
import "primereact/resources/themes/nova-light/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import { PanelMenu } from "primereact/panelmenu";
import { Menubar } from "primereact/menubar";
import { Button } from "primereact/button";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Badge } from "react-bootstrap";
class WebMapView extends React.Component {
  constructor(props) {
    super(props);
    this.mapRef = React.createRef();
  }

  addElement = (storepoint, att, temp) => {
    storepoint.popupTemplate = temp;
    storepoint.attributes = att;
  };

  sendHttpRequest = (method, url, data) => {
    const promise = new Promise((resolve, reject) => {
      const xhr = new XMLHttpRequest();
      xhr.open(method, url);

      xhr.responseType = "json";

      if (data) {
        xhr.setRequestHeader("Content-Type", "application/json");
      }

      xhr.onload = () => {
        if (xhr.status >= 400) {
          reject(xhr.response);
        } else {
          resolve(xhr.response);
        }
      };

      xhr.onerror = () => {
        reject("Something went wrong!");
      };

      xhr.send(JSON.stringify(data));
    });
    return promise;
  };

  state = {
    establishment: [],
    size: {},
    uni: {},
    Classrooms: [],
    floor0: [],
    floor1: [],
    floor2: [],
    floor3: [],
    displayfloor: [],
    inner: <div></div>,
    layers: [],
    mapr: {},
    blockLabel: [],
    floor: 0,
    defaultitems: [
      {
        label: "Locaux ",
        icon: "pi pi-fw pi-map-marker",

        items: [
          {
            label: " Jardin",
            command: (event) => {
              this.filtreByGardens();
            },
          },
          {
            label: " WC",
            command: (event) => {
              this.filtreByWc();
            },
          },

          {
            label: "Biblioteque",
            command: (event) => {
              this.filtreByBiblio();
            },
          },
          {
            label: "Parking",
            command: (event) => {
              this.filtreByParkings();
            },
          },
        ],
      },
      {
        label: "Déplacer",
        icon: "pi pi-directions",
        items: [
          {
            label: "Bus",
            command: (event) => {
              this.TransportByBus();
            },
          },
          {
            label: "Grand Taxi ",
            command: (event) => {
              this.TransportByTaxi();
            },
          },
        ],
      },
      {
        label: "Etudier",
        icon: "pi pi-fw pi-user",
        items: [
          {
            separator: true,
          },
          {
            label: "Les Salles",
            icon: "pi pi-fw pi-users",
            items: [],
          },
        ],
      },
      {
        label: "Switch Vers 3D",
        url: "/Map3D",
        command: (event) => {
          return <Link to="/Map3D" />;
        },
      },
    ],
    items: [
      {
        label: "Locaux ",
        icon: "pi pi-fw pi-map-marker",
        disabled: true,

        items: [
          {
            label: " Jardin",
            command: (event) => {
              this.filtreByGardens();
            },
          },
          {
            label: " WC",
            command: (event) => {
              this.filtreByWc();
            },
          },

          {
            label: "Biblioteque",
            command: (event) => {
              this.filtreByBiblio();
            },
          },
          {
            label: "Parking",
            command: (event) => {
              this.filtreByParkings();
            },
          },
        ],
      },
      {
        label: "Déplacer",
        icon: "pi pi-directions",
        disabled: true,
        items: [
          {
            label: "Bus",
            command: (event) => {
              this.TransportByBus();
            },
          },
          {
            label: "Grand Taxi ",
            command: (event) => {
              this.TransportByTaxi();
            },
          },
        ],
      },
      {
        label: "Etudier",
        icon: "pi pi-fw pi-user",
        disabled: true,
        items: [
          {
            separator: true,
          },
          {},
        ],
      },
      {
        label: "Switch Vers 3D",
        url: "/Map3D",
        command: (event) => {
          return <Link to="/Map3D" />;
        },
      },
    ],
    menuitems: [
      {
        label: "ETABLISSEMENTS",
        icon: "pi pi-fw pi-home",
        items: [
          {
            //*******************update******************
            label: "Site Fès-Dhar El Mehraz",
            icon: "pi pi-fw pi-plus",
            items: [
              {
                label: "Faculté des Sciences",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(7);
                },
              },
              {
                label: "Faculté des Lettres et des Sciences Humaines",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(6);
                },
              },
              {
                label: "Faculté des Sciences Juridiques Economique et Sociales",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(5);
                },
              },
            ],
          },
          {
            separator: true,
          },
          {
            label: "Site de Fès-Sais",
            icon: "pi pi-fw pi-plus",
            items: [
              {
                label: "Faculté des Sciences et Techniques",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(3);
                },
              },
              {
                label: "Faculté des Lettres et des Sciences Humaines",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo();
                },
              },
              {
                label: "Ecole Supérieure de Technologie",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(1);
                },
              },
              {
                label: "Faculté de Médecine et de Pharmacie",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(8);
                },
              },
              {
                label: "Ecole Nationale des Sciences Appliquées",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(2);
                },
              },
              {
                label: "Ecole Nationale de Commerce et de Gestion",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo(4);
                },
              },

              {
                label: "كلية الشريعة",
                icon: "pi pi-map-marker",
                command: (event) => {
                  this.ChangeLocationTo();
                },
              },
            ],
          },
        ],
      },
      {
        label: "Localiser",
        icon: "pi pi-fw pi-pencil",
        items: [],
      },
      {
        label: "Changer de Style",
        icon: "pi pi-fw pi-plus",
        items: [
          {
            label: "Hybrid",
            icon: "pi pi-fw pi-plus",
            command: (event) => {
              this.view.map.basemap = "hybrid";
            },
          },
          {
            label: "satellite",
            icon: "pi pi-fw pi-plus",
            command: (event) => {
              this.view.map.basemap = "satellite";
            },
          },
          {
            label: "streets-night",
            icon: "pi pi-fw pi-plus",
            command: (event) => {
              this.view.map.basemap = "streets-night-vector";
            },
          },
          {
            label: "dark-gray-vector",
            icon: "pi pi-fw pi-plus",
            command: (event) => {
              this.view.map.basemap = "dark-gray-vector";
            },
          },
        ],
      },
      {
        separator: true,
      },
    ],
    center: [],
    routeG: null,
    route: 0,
    f: 0,
    classmarker: [],
    filtre: 0,
    filtreByGardens: [],
    graphicLayer: {},
    clickG: 0,
    filtreByParkings: [],
    clickP: 0,
    filtreByBiblio: [],
    clickB: 0,
    filtreByWc: [],
    clickWc: 0,
    initialUniv: ["est", "ensa", "fst", "encg", "fsjes", "fldm", "fsdm", "fmp"],
    univs: null,
    transportByBus: [],
    clickTB: 0,
    transportByTaxi: [],
    clickTT: 0,
  };
  getLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(this.showPosition);
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  };
  showPosition = (position) => {
    let lo = [position.coords.latitude, position.coords.longitude];
    console.log(lo);
    this.setState({ center: lo });
  };

  diplayRoot = (univ, b) => {
    loadModules(
      [
        "esri/tasks/RouteTask",
        "esri/tasks/support/RouteParameters",
        "esri/tasks/support/FeatureSet",
        "esri/geometry/Point",
        "esri/Color",
        "esri/Graphic",
        "esri/symbols/SimpleLineSymbol",
        "esri/layers/GraphicsLayer",
        "esri/symbols/PictureMarkerSymbol",
      ],
      { css: true }
    ).then(
      ([
        RouteTask,
        RouteParameters,
        FeatureSet,
        Point,
        Color,
        Graphic,
        SimpleLineSymbol,
        GraphicsLayer,
        PictureMarkerSymbol,
      ]) => {
        this.view.map.removeAll();
        this.view.graphics.removeAll();
        var me = new PictureMarkerSymbol(
          "https://i.ibb.co/Ss9h6jM/kisspng-computer-icons-location-google-maps-location-icon-5abbd68ccd9df4-0682824415222595968422.png",
          20,
          20
        );
        var myl = new Point({
          x: this.state.center[1],
          y: this.state.center[0],
        });
        var graphic = new Graphic(myl, me);

        var graphicb = new Graphic(univ.markerPoint, univ.marker);

        var attributes = {
          id: b.id,
          name: b.name,
          datc: b.dateC,
          x: b.longitude,
          y: b.latitude,
          univ: "true",
        };
        graphicb.attributes = attributes;
        this.view.graphics.add(graphic);
        this.view.graphics.add(graphicb);
        let layer = new GraphicsLayer();
        layer.add(graphicb);

        this.view.map.add(layer);
        this.setState({ routeG: [graphic, graphicb] });
        console.log(this.state.routeG);
      }
    );
    this.showRoot();
  };
  showRoot = () => {
    loadModules(
      [
        "esri/tasks/RouteTask",
        "esri/tasks/support/RouteParameters",
        "esri/tasks/support/FeatureSet",
        "esri/layers/GraphicsLayer",
      ],
      { css: true }
    ).then(([RouteTask, RouteParameters, FeatureSet, GraphicsLayer]) => {
      // Setup the route parameters
      var routeTask = new RouteTask({
        url:
          "https://utility.arcgis.com/usrsvcs/appservices/PzZ99q7GolhzrIAg/rest/services/World/Route/NAServer/Route_World/solve",
      });
      var routeParams = new RouteParameters({
        stops: new FeatureSet({
          features: this.state.routeG,
        }),
        returnDirections: true,
      });

      // Get the route
      routeTask.solve(routeParams).then((data) => {
        data.routeResults.forEach((result) => {
          result.route.symbol = {
            type: "simple-line",
            color: [0, 255, 0, 1],
            width: 3,
          };
          console.log("done");
          this.view.graphics.add(result.route);
          console.log("done");
        });
      });
      this.view.zoom = 13;
    });
  };
  handelPlusClick = () => {
    if (this.state.f === 0) {
      this.setState({ floor: 0 });
    }
    if (this.state.floor === 4 || this.state.f === 0) {
      return;
    }
    this.setState({ floor: this.state.floor + 1 });
    this.view.zoom = 18.5;
    this.displayClasses();
  };
  handelMinusClick = () => {
    if (this.state.f === 0) {
      this.setState({ floor: 0 });
    }
    if (this.state.floor === 0 || this.state.f === 0) {
      return;
    }
    this.setState({ floor: this.state.floor - 1 });
    this.view.zoom = 18.5;
    this.displayClasses();
  };
  formatduconteur = () => {
    return this.state.floor;
  };
  displayClasses = () => {
    loadModules(["esri/Graphic", "esri/layers/GraphicsLayer"], {
      css: true,
    }).then(([Graphic, GraphicsLayer]) => {
      this.state.classmarker.forEach((layer) => {
        this.view.map.remove(layer);
      });
      let clr = [];
      var floor = [];
      if (this.state.floor === 4) {
        floor = this.state.floor0;
      }
      if (this.state.floor === 1) {
        floor = this.state.floor1;
      }
      if (this.state.floor === 2) {
        floor = this.state.floor2;
      }
      if (this.state.floor === 3) {
        floor = this.state.floor3;
      }

      floor.forEach((cl) => {
        let polygon = {
          type: "polygon",
        };

        let coor = [];

        for (let i = 0; i < cl.latitude.length; i++) {
          coor.push([cl.latitude[i], cl.longitude[i]]);
        }
        polygon.rings = coor;

        let simpleFillSymbol = {
          type: "simple-fill",
          color: [52, 73, 94, 0.7], // dark blue, opacity 80%
          outline: {
            color: [255, 255, 255],
            width: 1,
          },
        };
        var attributes = {
          id: cl.id,
          name: cl.name,
          equipments: cl.equipments,
          rj45: cl.rj45_outlets,
          nbplace: cl.nbplace,
          classroom: "true",
        };
        var template = {
          title: "<b><i>Informations</i></b>",
          content:
            "<ul><li><b>name: </b>" +
            cl.name +
            "</li><li><b>equipments:</b>" +
            cl.equipments +
            "</li><li><b>Ports RJ45 : </b>" +
            cl.rj45_outlets +
            "</li><li><b>Nombre des Places</b>" +
            cl.places +
            "</li></ul>",
        };
        let polygonGraphic = new Graphic({
          geometry: polygon,
          symbol: simpleFillSymbol,
          attributes: attributes,
          popupTemplate: template,
        });
        polygonGraphic.attributes = attributes;
        let layer = new GraphicsLayer();
        layer.add(polygonGraphic);
        clr.push(layer);
        this.view.map.add(layer);
      });
      this.setState({ classmarker: clr });
    });
  };
  removeAllPolygons = () => {
    if (this.state.classmarker.length !== 0) {
      this.state.classmarker.forEach((layer) => {
        this.view.map.remove(layer);
      });
    }
  };
  getAllClassrooms = () => {
    const xhr = new XMLHttpRequest();

    xhr.open("GET", "http://localhost:8080/Classrooms/list");

    let letfloor0 = [];
    let letfloor1 = [];
    let letfloor2 = [];
    let letfloor3 = [];
    xhr.onload = () => {
      const classrooms = JSON.parse(xhr.response);

      classrooms.forEach((c) => {
        if (c.floor === 4) letfloor0.push(c);
        if (c.floor === 1) letfloor1.push(c);
        if (c.floor === 2) letfloor2.push(c);
        if (c.floor === 3) letfloor3.push(c);
      });
      this.setState({ floor0: letfloor0 });

      this.setState({ floor1: letfloor1 });

      this.setState({ floor2: letfloor2 });

      this.setState({ floor3: letfloor3 });
    };
    xhr.send();
  };
  getTheSize = (q) => {
    const xhr = new XMLHttpRequest();

    xhr.open("GET", "http://localhost:8080/Classrooms/size?q=" + q);
    console.log(q);
    console.log("part 1");
    xhr.onload = () => {
      const s = JSON.parse(xhr.response);
      console.log("size");
      console.log(s);
      this.setState({ size: s });
    };
    xhr.send();
  };
  removeAllGraphics = () => {
    this.view.zoom = 15;
    this.setState({ clickG: 0 });
    this.setState({ clickP: 0 });
    this.setState({ clickB: 0 });
    this.setState({ clickWc: 0 });
    this.setState({ filtre: 0 });
    this.view.map.removeAll();
    this.state.layers.forEach((layer) => this.view.map.add(layer));
  };
  removeAll = () => {
    //this.setState({ items: this.state.defaultitems });
    let itemspanel = this.state.items;

    itemspanel.forEach((e) => (e.disabled = true));
    itemspanel[3].disabled = false;
    this.view.zoom = 15;
    this.view.center = [-5.002349, 33.989212];
    this.setState({ clickG: 0 });
    this.setState({ clickP: 0 });
    this.setState({ clickB: 0 });
    this.setState({ clickWc: 0 });
    this.setState({ filtre: 0 });
    this.view.graphics.removeAll();
    this.view.map.removeAll();
    this.state.layers.forEach((layer) => this.view.map.add(layer));
  };
  filtreByGardens = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickG === 0) {
          this.setState({ clickG: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();

            this.state.filtreByGardens.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
            this.state.filtre = 1;
          } else {
            this.state.filtreByGardens.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickG: 0 });
          this.state.filtreByGardens.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  filtreByParkings = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickP === 0) {
          this.setState({ clickP: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();

            this.state.filtreByParkings.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
            this.state.filtre = 1;
          } else {
            this.state.filtreByParkings.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickP: 0 });
          this.state.filtreByParkings.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  filtreByBiblio = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickB === 0) {
          this.setState({ clickB: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();

            this.state.filtreByBiblio.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
            this.state.filtre = 1;
          } else {
            this.state.filtreByBiblio.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickB: 0 });
          this.state.filtreByBiblio.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  filtreByWc = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickWc === 0) {
          this.setState({ clickWc: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();

            this.state.filtreByWc.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
            this.state.filtre = 1;
          } else {
            this.state.filtreByWc.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickWc: 0 });
          this.state.filtreByWc.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  test = () => {
    this.diplayRoot(this.state.uni);
  };
  ChangeLocationTo = (param) => {
    let data = this.state.establishment;
    console.log(param);
    data.forEach((est) => {
      if (est.id === param) {
        this.view.map.removeAll();
        this.state.layers.forEach((layer) => this.view.map.add(layer));
        this.view.zoom = 18;
        this.view.center = [est.longitude, est.latitude];
      }
    });
  };
  SelectedUniv = (param) => {
    console.log(param);
    switch (param) {
      case "est":
        this.ChangeLocationTo(1);
        break;
      case "ensa":
        this.ChangeLocationTo(2);
        break;
      case "fst":
        this.ChangeLocationTo(3);
        break;
      case "encg":
        this.ChangeLocationTo(4);
        break;
      case "fsjes":
        this.ChangeLocationTo(5);
        break;
      case "fldm":
        this.ChangeLocationTo(6);
        break;
      case "fsdm":
        this.ChangeLocationTo(7);
        break;
      case "fmp":
        this.ChangeLocationTo(8);
        break;
      default:
        this.ChangeLocationTo();
        break;
    }
  };
  suggestUnivs(event) {
    let results = this.state.initialUniv.filter((univ) => {
      return univ.toLowerCase().startsWith(event.query.toLowerCase());
    });

    this.setState({ univs: results });
  }
  TransportByBus = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickTB === 0) {
          this.setState({ clickTB: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();
            console.log(this.state.transportByBus);
            this.state.transportByBus.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          } else {
            this.state.transportByBus.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickTB: 0 });
          this.state.transportByBus.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  TransportByTaxi = () => {
    loadModules(["esri/layers/GraphicsLayer"], { css: true }).then(
      ([GraphicsLayer]) => {
        var graphic = new GraphicsLayer();
        if (this.state.clickTT === 0) {
          this.setState({ clickTT: 1 });
          if (this.state.filtre === 0) {
            this.setState({ filtre: 1 });
            this.view.map.removeAll();
            console.log(this.state.transportByTaxi);
            this.state.transportByTaxi.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          } else {
            this.state.transportByTaxi.forEach((layer) => {
              graphic.add(layer);
              this.view.map.add(graphic);
            });
          }
        } else {
          this.setState({ clickTT: 0 });
          this.state.transportByTaxi.forEach((layer) => {
            graphic.add(layer);
            this.view.map.remove(graphic);
          });
        }
      }
    );
  };
  async componentWillMount() {
    this.getLocation();
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/Establishments/all");
    xhr.onload = () => {
      const data = JSON.parse(xhr.response);
      this.setState({ establishment: data });

      loadModules(
        [
          "esri/Map",
          "esri/WebMap",
          "esri/views/MapView",
          "esri/symbols/PictureMarkerSymbol",
          "esri/geometry/Point",
          "esri/Graphic",
          "esri/layers/GraphicsLayer",
          "esri/core/watchUtils",
          "esri/widgets/Locate",
        ],
        { css: true }
      ).then(
        ([
          ArcGISMap,
          WebMap,
          MapView,
          PictureMarkerSymbol,
          Point,
          Graphic,
          GraphicsLayer,
          watchUtils,
          Locate,
        ]) => {
          const map = new ArcGISMap({
            basemap: "hybrid",
          });

          this.setState({ mapW: map });
          this.view = new MapView({
            container: this.mapRef.current,
            map: map,
            center: [-5.002349, 33.989212],
            zoom: 15,
          });
          this.view.when(() => {
            let arrayLayer = [];
            this.getAllClassrooms();

            data.forEach((est) => {
              var markerPoint = new Point({
                x: est.longitude,
                y: est.latitude,
              });
              var attributes = {
                id: est.id,
                name: est.name,
                datc: est.dateC,
                x: est.longitude,
                y: est.latitude,
                univ: "true",
              };

              var storepoint = new Graphic(markerPoint, marker);
              storepoint.attributes = attributes;

              var layer = new GraphicsLayer();
              layer.add(storepoint);
              arrayLayer.push(layer);

              map.add(layer);
            });
            let names = [];
            //test

            data.forEach((b) => {
              var markerPoint = new Point({
                x: b.longitude,
                y: b.latitude,
              });
              this.setState({ uni: { markerPoint, marker } });
              let labl = {
                label: b.name,
                graphic: { markerPoint, marker },
                command: (event) => {
                  this.diplayRoot({ markerPoint, marker }, b);
                },
              };
              names.push(labl);
            });

            let itemspanel = this.state.menuitems;
            names.forEach((name) => itemspanel[1].items.push(name));
            this.setState({ layers: arrayLayer });
          });
          this.setState({ mapW: this.view.map });
          let graphicL = new GraphicsLayer();
          this.setState({ graphicLayer: graphicL });
          var marker = new PictureMarkerSymbol(
            "http://static.arcgis.com/images/Symbols/Shapes/BluePin1LargeB.png",
            34,
            34
          );
          this.setState({ newmarker: marker });

          var Jardin = new PictureMarkerSymbol(forest, 20, 20);
          var car = new PictureMarkerSymbol(Parking, 20, 20);
          var restaurant = new PictureMarkerSymbol(restaurantl, 20, 20);
          var stade = new PictureMarkerSymbol(stadel, 20, 20);
          var buvette = new PictureMarkerSymbol(buvettel, 20, 20);
          var biblio = new PictureMarkerSymbol(bibliol, 20, 20);
          var wc = new PictureMarkerSymbol(Wcl, 20, 20);
          var bus = new PictureMarkerSymbol(busl, 30, 30);
          var taxi = new PictureMarkerSymbol(taxil, 20, 20);
          var blockm = new PictureMarkerSymbol(
            "http://static.arcgis.com/images/Symbols/Shapes/RedPin1LargeB.png",
            20,
            20
          );

          var dataun = this.state.establishments;

          this.view.on("click", (evt) => {
            this.view.hitTest(evt.screenPoint).then((response) => {
              var graphic = response.results[0].graphic;
              if (graphic) {
                if (graphic.attributes.univ === "true") {
                  let itemspanel = this.state.items;
                  itemspanel[2].items = [];
                  itemspanel.forEach((e) => (e.disabled = false));
                  this.view.graphics.removeAll();
                  this.view.map.removeAll();
                  this.setState({ f: 1 });
                  this.view.zoom = 17;
                  this.view.center = [
                    graphic.attributes.x,
                    graphic.attributes.y,
                  ];
                  let gardens = [];
                  let parkings = [];
                  let biblios = [];
                  let wcs = [];
                  let taxis = [];
                  let buss = [];
                  // console.log(this.state.establishments);
                  let data = this.state.establishment;

                  data.forEach((est) => {
                    if (est.id === graphic.attributes.id) {
                      let s = est.structures;
                      let b = est.blocks;
                      let pavillon = [];
                      b.forEach((block) => {
                        var markerPoint = new Point({
                          x: block.longitude,
                          y: block.latitude,
                        });
                        var storepoint = new Graphic(markerPoint, blockm);

                        var template = {
                          title: "Informations du Block",
                          content:
                            "<ul><li><b>name: </b>" +
                            block.name +
                            "</li><li><b>nombre Des Classes:</b> " +
                            block.size +
                            "</li></ul>",
                        };
                        pavillon.push({
                          name: block.name,
                          center: [block.longitude, block.latitude],
                        });
                        var attributes = {
                          id: block.id,
                          name: block.name,
                          block: "true",
                          x: block.longitude,
                          y: block.latitude,
                        };
                        /*var infoTemplate = new InfoTemplate(est.name, est.dateC);*/
                        var storepoint = new Graphic(markerPoint, blockm);
                        //this.addElement(storepoint, attributes, );
                        storepoint.attributes = attributes;
                        storepoint.popupTemplate = template;
                        // console.log(storepoint);
                        var layer = new GraphicsLayer();
                        layer.add(storepoint);
                        map.add(layer);
                      });

                      let names = [];
                      pavillon.forEach((b) => {
                        let labl = {
                          label: b.name,
                          command: (event) => {
                            this.view.center = b.center;
                            this.view.zoom = 21;
                          },
                        };
                        names.push(labl);
                      });
                      let itemspanel = this.state.items;
                      names.forEach((name) => itemspanel[2].items.push(name));

                      this.setState({ items: itemspanel });

                      s.forEach((est) => {
                        var markerPoint = new Point({
                          x: est.longitude,
                          y: est.latitude,
                        });
                        console.log(est.name);
                        switch (est.name) {
                          case "Jardin":
                            var storepoint = new Graphic(markerPoint, Jardin);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li>name: " +
                                est.name +
                                "</li><li>description:" +
                                est.description +
                                "</li><li>Accés internet :" +
                                est.a_internet +
                                "</li><li>nombre des Places : " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            gardens.push(storepoint);
                            break;
                          case "Parking":
                            storepoint = new Graphic(markerPoint, car);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };

                            template = {
                              title: "Informations",
                              content:
                                "<img class='popupimg' src='https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/tc10tc1n81995uffz64n.jpg' /><br>" +
                                "<ul><li><b>name: </b>" +
                                est.name +
                                "</li><li><b>description:</b>" +
                                est.description +
                                "</li><li><b>heure d'ouverture : </b>" +
                                est.h_Begin +
                                "</li><li><b>heure de fermeture :</b>" +
                                est.h_End +
                                "</li><li><b>nombre des Places :</b> " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            parkings.push(storepoint);
                            break;
                          case "Buvette":
                            storepoint = new Graphic(markerPoint, buvette);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li>name: " +
                                est.name +
                                "</li><li>description:" +
                                est.description +
                                "</li><li>heure d'ouverture : " +
                                est.h_Begin +
                                "</li><li>heure de fermeture :" +
                                est.h_End +
                                "</li><li>Accés internet :" +
                                est.a_internet +
                                "</li><li>nombre des Places : " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            break;
                          case "Bibliotheque":
                            storepoint = new Graphic(markerPoint, biblio);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li>name: " +
                                est.name +
                                "</li><li>description:" +
                                est.description +
                                "</li><li>heure d'ouverture : " +
                                est.h_Begin +
                                "</li><li>heure de fermeture :" +
                                est.h_End +
                                "</li><li>Accés internet :" +
                                est.a_internet +
                                "</li><li>nombre des Places : " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            biblios.push(storepoint);
                            break;
                          case "Terrain":
                            storepoint = new Graphic(markerPoint, stade);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li><b>name:</b> " +
                                est.name +
                                "</li><li><b>description:</b>" +
                                est.description +
                                "</li><li><b>heure d'ouverture :</b> " +
                                est.h_Begin +
                                "</li><li><b>heure de fermeture :</b>" +
                                est.h_End +
                                "</li><li><b>nombre des Joueur dans chaque équipe :</b> " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            break;
                          case "TAXI":
                            storepoint = new Graphic(markerPoint, taxi);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };

                            template = {
                              title: "Informations",
                              content:
                                "<img class='popupimg' src='https://i.ibb.co/VH2mq7y/taxig.jpg' /><br>" +
                                "<ul><li><b>name: </b>" +
                                est.name +
                                "</li><li><b>Destination:</b>" +
                                est.description +
                                "</li><li><b>Heures de disponibilité : </b>" +
                                est.h_Begin +
                                " jusqu'à " +
                                est.h_End +
                                "</li><li><b>nombre des Places :</b> " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            taxis.push(storepoint);
                            break;
                          case "BUS":
                            console.log(est.longitude);
                            console.log(est.latitude);
                            storepoint = new Graphic(markerPoint, bus);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };

                            template = {
                              title: "Informations",
                              content:
                                "<img class='popupimg' src='https://i.ibb.co/RyfNw9s/busF.jpg' /><br>" +
                                "<ul><li><b>name: </b>" +
                                est.name +
                                "</li><li><b>Destination:</b>" +
                                est.description +
                                "</li><li><b>Heures de disponibilité : </b>" +
                                est.h_Begin +
                                "jusqu'à" +
                                est.h_End +
                                "</li><li><b>nombre des Places :</b> " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            buss.push(storepoint);
                            break;
                          case "Restaurant":
                            storepoint = new Graphic(markerPoint, restaurant);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                              he: est.date_end,
                              hb: est.date_begin,
                              internet: est.a_internet,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li>name: " +
                                est.name +
                                "</li><li>description:" +
                                est.description +
                                "</li><li>heure d'ouverture : " +
                                est.h_Begin +
                                "</li><li>heure de fermeture :" +
                                est.h_End +
                                "</li><li>Accés internet :" +
                                est.a_internet +
                                "</li><li>nombre des Places : " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            break;
                          case "WC":
                            storepoint = new Graphic(markerPoint, wc);
                            var attributes = {
                              id: est.id,
                              name: est.name,
                              desc: est.description,
                            };
                            var template = {
                              title: "Informations",
                              content:
                                "<ul><li>name: " +
                                est.name +
                                "</li><li>description:" +
                                est.description +
                                "</li><li>nombre des cabinets : " +
                                est.nbplace +
                                "</li></ul>",
                            };
                            this.addElement(storepoint, attributes, template);
                            wcs.push(storepoint);
                            break;
                        }

                        var layer = new GraphicsLayer();

                        layer.add(storepoint);

                        map.add(layer);
                      });
                      this.setState({ filtreByGardens: gardens });
                      this.setState({ filtreByParkings: parkings });
                      this.setState({ filtreByBiblio: biblios });
                      this.setState({ filtreByWc: wcs });
                      this.setState({ transportByBus: buss });
                      this.setState({ transportByTaxi: taxis });
                    }
                  });
                }
              }
            });
          });
          watchUtils.whenTrue(this.view, "stationary", (evt) => {
            if (this.view.extent) {
              if (this.view.zoom < 15) {
                // this.setState({ inner: <div></div> });

                this.removeAllPolygons();
              }
            }
          });

          let data = this.state.establishment;
        }
      );
    };
    xhr.send();
  }

  render() {
    {
      return (
        <div>
          <div className="content-section implementation">
            <Menubar style={{}} model={this.state.menuitems}>
              <AutoComplete
                placeholder="Search"
                value={this.state.univ}
                onChange={(e) => this.setState({ univ: e.value })}
                suggestions={this.state.univs}
                completeMethod={this.suggestUnivs.bind(this)}
                onSelect={(e) => this.SelectedUniv(e.value)}
              />
              <Link to="/">
                <Button
                  label="Logout"
                  icon="pi pi-power-off"
                  style={{ marginLeft: 4 }}
                />
              </Link>
            </Menubar>
          </div>
          <div>
            <div style={{ float: "left" }}>
              <PanelMenu model={this.state.items} style={{ width: "250px" }} />
            </div>

            <div style={{ float: "right" }}>
              <div class="my_button">
                <Button
                  label="Montrer tout les Establishments"
                  icon="pi pi-globe"
                  style={{ marginLeft: 4 }}
                  onClick={() => {
                    this.removeAll();
                    this.setState({ f: 0 });
                  }}
                />
              </div>

              <div class="etage">
                <span
                  style={{
                    backgroundColor: "#007ad9",
                    color: "#fff",
                    marginTop: 10,
                    marginRight: 8,
                    marginLeft: 4,
                    borderRadius: "0px",
                    width: 37,
                  }}
                  class="badge rn"
                >
                  Etage
                </span>
                <Button
                  label=" "
                  icon="pi pi-caret-up"
                  style={{
                    marginLeft: 4,
                    width: 37,
                    marginRight: 8,
                    borderRadius: "0px",
                  }}
                  onClick={this.handelPlusClick}
                />
                <Button
                  label=" "
                  style={{
                    marginLeft: 4,
                    marginRight: 8,
                    paddingBottom: 8,
                    borderRadius: "0px",
                    width: 37,
                  }}
                >
                  {this.formatduconteur()}
                </Button>

                <Button
                  icon="pi pi-caret-down"
                  style={{
                    marginLeft: 4,
                    marginRight: 8,
                    width: 37,
                    borderRadius: "0px",
                  }}
                  onClick={this.handelMinusClick}
                />
              </div>
              <div
                className="webmap"
                ref={this.mapRef}
                style={{ float: "right" }}
              ></div>
            </div>
          </div>
        </div>
      );
    }
  }
}

export default WebMapView;
