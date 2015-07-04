'use strict';

export default function (role) {
    var menuContent = [],
        navtest = {
            name: 'HeadingTest',
            type: 'heading',
            children: [{
                name: 'ToggleTest',
                type: 'toggle',
                pages: [{
                    name: 'Accueil',
                    state: 'main.home',
                    type: 'link'
                }, {
                    name: 'Quiz',
                    state: 'main.quiz',
                    type: 'link'
                }]
            }]
        },
        home = {
            name: 'Accueil',
            state: 'main.home',
            type: 'link'
        },
        quiz = {
            name: 'Quiz',
            state: 'main.quiz',
            type: 'link'
        };
    menuContent.push(navtest);
    menuContent.push(home);
    menuContent.push(quiz);
    return menuContent;
}