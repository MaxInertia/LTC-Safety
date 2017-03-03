//
//  LTCValueSelectionController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCValueSelectionController.h"
#import "LTCInputAlertController.h"
#import "LTCValueSelectionViewModel.h"
#import "LTCRowValue.h"

@interface LTCValueSelectionController () <LTCInputAlertControllerDelegate>
@property (nonatomic, strong) LTCValueSelectionViewModel *viewModel;
@end

@implementation LTCValueSelectionController
@dynamic fileName;
@dynamic hasOther;


/**
 The initializer called by XLForms used to load the value selection configuration file from disk and populate rows with the values found in that file.

 @return An LTCValueSelectionController object that has a row for each value in the loaded configuration file.
 @post The LTCValueSelectionController object has a non-nil form populated with a row for each value in the view model.
 */
- (instancetype)init{
    
    LTCValueSelectionViewModel *viewModel = [LTCValueSelectionViewModel selectionViewModelForFileName:self.fileName];
    XLFormDescriptor *form;
    XLFormSectionDescriptor *section;
    XLFormRowDescriptor *row;
    
    NSAssert(viewModel.title.length > 0, @"Value selection controller received a view model with an empty title.");

    form = [XLFormDescriptor formDescriptorWithTitle:viewModel.title];
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    // Create a row for each value within the view model.
    NSAssert(viewModel.rowValues.count > 0, @"Value selection controller received a view model with no rows.");
    for (LTCRowValue *rowValue in viewModel.rowValues) {
        row = [self _createRow:rowValue action:@selector(_selectCategory:)];
        [section addFormRow: row];
    }
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    // Create a row for accepting custom user input if none of the predefined values fit.
    LTCRowValue *otherRowValue = [[LTCRowValue alloc] initWithTag:@"VALUE_OTHER"];
    row = [self _createRow:otherRowValue action:@selector(_selectOther:)];
    if(self.hasOther){
        [section addFormRow: row];
    }
    
    if (self = [super initWithForm:form]) {
        self.viewModel = viewModel;
    }
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    NSAssert(self.form, @"Value selection controller finished initialization with a nil form");

    return self;
}

/**
 Creates an XLFormRowDescriptor for a specified row value.

 @param rowValue The row value who's tag and title should be used to create a new row descriptor.
 @param action The selector that is called when the row is tapped by the user.
 @return A non-nil row descriptor configured to match the row value.
 */
- (XLFormRowDescriptor *)_createRow:(LTCRowValue *)rowValue action:(SEL)action {
    
    NSAssert(rowValue != nil, @"Attempted to create a row with a nil row value.");
    NSAssert(rowValue.title != nil, @"Attempted to create a row with a nil row value title.");
    NSAssert(rowValue.tag != nil, @"Attempted to create a row with a nil row value tag.");
    NSAssert2([self respondsToSelector:action], @"Attempted to create a row with an action that this %@ does not respond to: %@", self.class, NSStringFromSelector(action));
    
    XLFormRowDescriptor *row = [XLFormRowDescriptor formRowDescriptorWithTag:rowValue.tag rowType:XLFormRowDescriptorTypeButton title:rowValue.title];
    [row.cellConfig setObject:@(NSTextAlignmentLeft) forKey:@"textLabel.textAlignment"];
    [row.cellConfig setObject:[UIColor blackColor] forKey:@"textLabel.textColor"];
    row.action.formSelector = action;
    return row;
}

/**
 The action that is triggered whenever the user taps a row in the value selection controller.

 @pre The category descriptor must be non-nil and have a non-empty title.
 @param categoryDescriptor The row descriptor for the tapped row.
 @post The value selection controller's rowDescriptor has a value corresponding the categoryDescriptor's title.
 */
- (void)_selectCategory:(XLFormRowDescriptor *)categoryDescriptor {

    NSAssert(categoryDescriptor != nil, @"Selected a nil category descriptor.");
    NSAssert(categoryDescriptor.title.length > 0, @"Selected a category descriptor with an empty title.");

    [self _finishWithValue:categoryDescriptor.title];
    
    NSAssert([categoryDescriptor.title isEqual:self.rowDescriptor.value], @"Failed to update the outgoing row descriptor value.");
}


/**
 The action that is triggered whenever the user specifies they want to provide custom input.

 @pre There is a string in Localizable.strings to provide a localized title for the row descriptor tag.
 @param descriptor The row descriptor for the other row that was tapped.
 @post An alert has been presented to accept the user input.
 */
- (void)_selectOther:(XLFormRowDescriptor *)descriptor {
    
    NSAssert(descriptor != nil, @"Selected other descriptor with nil value.");
    NSAssert(NSLocalizedString(descriptor.tag, nil) != nil, @"Missing title for other descriptor.");
    
    [self deselectFormRow:descriptor];
    
    LTCInputAlertController *inputAlert = [LTCInputAlertController alertControllerWithTitle:NSLocalizedString(descriptor.tag, nil) message:self.viewModel.otherPrompt preferredStyle:UIAlertControllerStyleAlert];
    inputAlert.delegate = self;
    [self presentViewController:inputAlert animated:YES completion:nil];
}

/**
 The delegate callback that is called when the user taps OK after entering custom input.

 @pre The input string that the user typed into the custom value textfield.
 @param controller The alert controller that the user entered their custom input into.
 @param input The custom input the user entered.
 @post The value selection controller's rowDescriptor has a value corresponding the user's custom input.
 */
- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input {
    
    NSAssert(input.length > 0, @"Value selection controller received empty custom value.");
    
    [self _finishWithValue:input];
    
    NSAssert([input isEqual:self.rowDescriptor.value], @"Failed to update the outgoing row descriptor value.");
}

/**
 Called when a value has been selected causing the value selection controller to be dismissed.

 @pre value.length >= 0
 @param value The value that the user selected.
 @post The value selection controller's rowDescriptor value is equal to the finish value
 */
- (void)_finishWithValue:(NSString *)value {
    
    NSAssert([value isEqualToString:@"testSelectCategory"] || self.navigationController != nil, @"Attempted to pop to a nil root view controller.");
    NSAssert(value.length > 0, @"Value selection controller selected an empty value.");
    
    self.rowDescriptor.value = value;
    [self.navigationController popToRootViewControllerAnimated:YES];
    
    NSAssert(self.rowDescriptor != nil, @"Value selection controller selected a value but there was no row descriptor to assign it to.");
}

@end
