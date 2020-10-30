import {Subject} from './subject';
import { InterInterval } from './interInteval';
import { InterIntervalService } from '../services/inter-interval.service';

export class Concept {
    id : number;
    subject : Subject;
    name : string;
    explanation : string;
    reviewed: boolean;
    simplified: boolean;
    done : boolean;
    interInterval : InterInterval;

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

    get Subject() : Subject {
        return this.subject;
    }

    set Subject(subject : Subject) {
        this.subject = subject;
    }

    get InterInterval() : InterInterval {
        return this.interInterval;
    }

    set InterInterval(interInteval : InterInterval) {
        this.interInterval = interInteval;
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