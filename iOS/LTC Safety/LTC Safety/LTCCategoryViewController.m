//
//  LTCCategoryTableViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCCategoryViewController.h"
#import "LTCCategoryLoader.h"
#import "LTCInputAlertController.h"

@interface LTCCategoryViewController () <LTCInputAlertControllerDelegate>

@end

@implementation LTCCategoryViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    assert(self.rowDescriptor.title != nil);
    
    self.title = self.rowDescriptor.title;
}

- (instancetype)init {
    
    XLFormDescriptor *form;
    XLFormSectionDescriptor *section;
    XLFormRowDescriptor *row;
    
    form = [XLFormDescriptor formDescriptorWithTitle:nil];
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    NSArray <LTCCategory *> *categories = [LTCCategoryLoader loadCategories];
    for (LTCCategory *category in categories) {
        row = [self _createRow:category.title action:@selector(_selectCategory:)];
        [section addFormRow: row];
    }
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    row = [self _createRow:NSLocalizedString(@"CATEGORY_OTHER", nil) action:@selector(_selectOther:)];
    [section addFormRow: row];
    
    return [super initWithForm:form];
}

- (XLFormRowDescriptor *)_createRow:(NSString *)title action:(SEL)action {
    
    assert(title != nil);
    assert(action != NULL);
    
    XLFormRowDescriptor *row = [XLFormRowDescriptor formRowDescriptorWithTag:title rowType:XLFormRowDescriptorTypeButton title:NSLocalizedString(title, nil)];
    [row.cellConfig setObject:@(NSTextAlignmentLeft) forKey:@"textLabel.textAlignment"];
    [row.cellConfig setObject:[UIColor blackColor] forKey:@"textLabel.textColor"];
    row.action.formSelector = action;
    return row;
}

- (void)_selectCategory:(XLFormRowDescriptor *)categoryDescriptor {

    assert(categoryDescriptor != nil);
    assert([categoryDescriptor isKindOfClass:[XLFormRowDescriptor class]]);
    
    [self _finishWithValue:categoryDescriptor.title];
}

- (void)_selectOther:(XLFormRowDescriptor *)descriptor {
    LTCInputAlertController *inputAlert = [LTCInputAlertController alertControllerWithTitle:NSLocalizedString(@"CATEGORY_OTHER", nil) message:NSLocalizedString(@"CATEGORY_OTHER_PROMPT", nil) preferredStyle:UIAlertControllerStyleAlert];
    inputAlert.delegate = self;
    [self presentViewController:inputAlert animated:YES completion:nil];
}

- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input {
    [self _finishWithValue:input];
}

- (void)_finishWithValue:(NSString *)value {
    
    assert(self.navigationController != nil);
    assert(value != nil);

    self.rowDescriptor.value = value;
    [self.navigationController popToRootViewControllerAnimated:YES];
}

@end
