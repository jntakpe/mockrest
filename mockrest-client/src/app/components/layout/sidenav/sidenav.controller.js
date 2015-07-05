'use strict';

export default class SidenavCtrl {

    constructor(SidenavService) {
        this.SidenavService = SidenavService;
        this.menuContent = SidenavService.getMenuContent('ROLE_ADMIN');
    }

    isSectionSelected(section) {
        var selected = false, openedSection = this.SidenavService.openedSection;
        if (openedSection === section) {
            selected = true;
        }
        else if (section.children) {
            section.children.forEach(function (childSection) {
                if (childSection === openedSection) {
                    selected = true;
                }
            });
        }
        return selected;
    }

    isSelected(page) {
        return this.SidenavService.isPageSelected(page);
    }

    isOpen(section) {
        return this.SidenavService.isSectionSelected(section);
    }

    toggleOpen(section) {
        this.SidenavService.toggleSelectSection(section);
    }
}
