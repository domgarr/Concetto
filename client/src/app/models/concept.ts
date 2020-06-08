import { typeWithParameters } from '@angular/compiler/src/render3/util';

export class Concept {
    id : number;
    userId : number;
    name : string;
    explanation : string;
    reviewed: boolean;
    simplified: boolean;
    done : boolean;

    constructor(){
        this.name = "";
        this.explanation = "";
        this.reviewed = false;
        this.simplified = false;
    }

    get Id() : number {
        return this.id;
    }

    set Id(id : number) {
        this.id = id;
    }

    get UserId() : number {
        return this.userId;
    }

    set UserId(userId : number) {
        this.userId = userId;
    }

    get Name() : string {
        return this.name;
    }

    set Name(name : string){
        this.name = name;
    }

    get Explanation() : string {
        return this.explanation;
    }

    set Explanation(explanation : string){
        this.explanation = explanation;
    }

    get Reviewed() : boolean {
        return this.reviewed;
    }

    set Reviewed(reviewed : boolean) {
        this.reviewed = reviewed;
    }

    get Simplified() : boolean {
        return this.simplified;
    }

    set Simplified(simplified : boolean) {
        this.simplified = simplified;
    }

    


}