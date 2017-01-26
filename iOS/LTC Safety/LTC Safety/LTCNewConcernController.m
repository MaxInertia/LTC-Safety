//
//  LTCNewConcernController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCNewConcernController.h"
#import "LTCCategoryViewController.h"
#import "LTCImagePickerCell.h"

NSString *const NMRowDescriptorTitle            = @"First and Last Name";
NSString *const NMRowDescriptorLocation         = @"EVENT_LOCATION";
NSString *const NMRowDescriptorDescription      = @"EVENT_DESCRIPTION";
NSString *const NMRowDescriptorAllDay           = @"EVENT_ALL_DAY";
NSString *const NMRowDescriptorStartDate        = @"EVENT_START_DATE";
NSString *const NMRowDescriptorEndDate          = @"EVENT_END_DATE";
NSString *const NMRowDescriptorRepeat           = @"Nature of Concern";
NSString *const NMRowDescriptorChildrenAllowed  = @"EVENT_CHILDREN_ALLOWED";

@interface LTCNewConcernController ()

@end

@implementation LTCNewConcernController

- (NSCalendar *)calendar {
    static dispatch_once_t once;
    static NSCalendar *calendar;
    dispatch_once(&once, ^{
        calendar = [NSCalendar autoupdatingCurrentCalendar];
    });
    return calendar;
}

- (instancetype)init {
  
    
    NSMutableDictionary *rowDescriptorTypes = [XLFormViewController cellClassesForRowDescriptorTypes];
    [rowDescriptorTypes setObject:[LTCImagePickerCell class] forKey:@"Test Row"];

    
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancel)];
    self.navigationItem.rightBarButtonItem.enabled = NO;
    
    XLFormDescriptor *form;
    XLFormSectionDescriptor *section;
    XLFormRowDescriptor *row;
    
    form = [XLFormDescriptor formDescriptorWithTitle:NSLocalizedString(@"New Concern", nil)];
    
    section = [XLFormSectionDescriptor formSection];
    section.title = @"What is your name?";
    [form addFormSection:section];
    
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:NMRowDescriptorTitle rowType:XLFormRowDescriptorTypeName];
    [row.cellConfigAtConfigure setObject:NSLocalizedString(NMRowDescriptorTitle, nil) forKey:@"textField.placeholder"];
    row.required = YES;
    [section addFormRow:row];
    
 
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:@"Tag" rowType:XLFormRowDescriptorTypeSelectorPush title:@"Nature of Concern"];
    row.action.viewControllerClass = [LTCCategoryViewController class];
    [section addFormRow:row];

    
    section = [XLFormSectionDescriptor formSection];
    section.title = @"What actions have been taken?";
    [form addFormSection:section];
    
    
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:NMRowDescriptorLocation rowType:XLFormRowDescriptorTypeTextView];
    row.required = YES;
    [section addFormRow:row];
    
    section = [XLFormSectionDescriptor formSection];
    section.title = @"Photos";
    [form addFormSection:section];
    
    
    
    
    // Title
    //row = [XLFormRowDescriptor formRowDescriptorWithTag:@"image" rowType:XLFormRowDescriptorTypeImage title:@"Add Image"];
    //[section addFormRow: row];
    
    // Title
    row = [XLFormRowDescriptor formRowDescriptorWithTag:@"image" rowType:@"Test Row" title:@"Add Photo"];
    [section addFormRow: row];
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:@"concern" rowType:XLFormRowDescriptorTypeButton title:@"Submit Concern"];
    row.action.formSelector = @selector(_submit);
    [section addFormRow: row];
    
    
    return [super initWithForm:form];
}

- (UIStoryboard *)storyboardForRow:(XLFormRowDescriptor *)formRow {
    return [UIStoryboard storyboardWithName:@"EventCreation" bundle:nil];
}

- (void)_submit {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)cancel {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)formRowDescriptorValueHasChanged:(XLFormRowDescriptor *)rowDescriptor oldValue:(id)oldValue newValue:(id)newValue {
    [super formRowDescriptorValueHasChanged:rowDescriptor oldValue:oldValue newValue:newValue];
    self.navigationItem.rightBarButtonItem.enabled = self.formValidationErrors.count <= 0;
}

@end
