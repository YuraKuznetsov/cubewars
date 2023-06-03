export class CubeShower {

    sizeClassForDimension = {
        2: "two",
        3: "three",
        4: "four",
        5: "five",
        6: "six",
        7: "seven"
    }

    constructor(cubeDiv) {
        this.topSideDiv = cubeDiv.find("#top-side");
        this.frontSideDiv = cubeDiv.find("#front-side");
        this.bottomSideDiv = cubeDiv.find("#bottom-side");
        this.leftSideDiv = cubeDiv.find("#left-side");
        this.rightSideDiv = cubeDiv.find("#right-side");
        this.backSideDiv = cubeDiv.find("#back-side");
    }

    show(cube) {
        this.#fillSideDiv(this.topSideDiv, cube.topSide);
        this.#fillSideDiv(this.frontSideDiv, cube.frontSide);
        this.#fillSideDiv(this.bottomSideDiv, cube.bottomSide);
        this.#fillSideDiv(this.leftSideDiv, cube.leftSide);
        this.#fillSideDiv(this.rightSideDiv, cube.rightSide);
        this.#fillSideDiv(this.backSideDiv, cube.backSide);
    }

    #fillSideDiv(sideDiv, sideInfo) {
        sideDiv.html("");

        const dimension = sideInfo["dimension"];
        const matrix = sideInfo["elementsMatrix"];

        for (let i = 0; i < dimension; i++) {
            for (let j = 0; j < dimension; j++) {
                const detailColor = matrix[i][j].color.toLowerCase();
                const detailSizeClass = this.sizeClassForDimension[dimension];

                const detail = $("<div class='cube__detail'></div>");
                detail.addClass(detailColor);
                detail.addClass(detailSizeClass);

                sideDiv.append(detail);
            }
        }
    }
}